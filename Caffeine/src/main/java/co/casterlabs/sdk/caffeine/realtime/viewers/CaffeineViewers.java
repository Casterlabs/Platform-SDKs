package co.casterlabs.sdk.caffeine.realtime.viewers;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.realtime.WSListener;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.realtime.ReaperWSConnection;
import co.casterlabs.sdk.caffeine.requests.CaffeineUserInfoRequest;
import co.casterlabs.sdk.caffeine.types.CaffeineUser;
import co.casterlabs.sdk.caffeine.types.UserBadge;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;

public class CaffeineViewers implements Closeable {
    private static final String AUTH_LOGIN_HEADER = "{\"Headers\":{\"Authorization\":\"Bearer %s\",\"X-Client-Type\":\"api\"},\"Body\":\"{\\\"user\\\":\\\"%s\\\"}\"}";

    //@formatter:off
    private static Viewer anonymousViewer = new Viewer(
        new ViewerDetails(
            "Anonymous", 
            "https://images.caffeine.tv/defaults/avatar-001.png", 
            UserBadge.NONE, 
            "anonymous"
        ), 
        null, 
        -1
    );
    //@formatter:on

    private @Setter @NonNull CaffeineAuth auth;
    private ReaperWSConnection conn;

    private @Setter @Nullable CaffeineViewersListener listener;

    public CaffeineViewers(@NonNull CaffeineAuth auth) {
        this.auth = auth;
        this.conn = new ReaperWSConnection(String.format(CaffeineEndpoints.VIEWERS, auth.getCaid().substring(4)), new Listener());
    }

    public void connect() throws InterruptedException {
        this.conn.connect();
    }

    public boolean isOpen() {
        return this.conn.isOpen();
    }

    @Override
    public void close() {
        this.conn.close();
    }

    private class Listener implements WSListener {
        private Map<String, Viewer> viewers = new HashMap<>();
        private int lastAnonymousCount = 0;

        @Override
        public void onOpen() {
            conn.send(String.format(AUTH_LOGIN_HEADER, auth.getAccessToken(), auth.getSignedToken()));

            if (listener != null) {
                listener.onOpen();
            }
        }

        @SneakyThrows
        @Override
        public void onMessage(String raw) {
            JsonObject json = CaffeineApi.RSON.fromJson(raw, JsonObject.class);

            if (json.containsKey("Compatibility-Mode") || json.containsKey("status")) {
                return; // Some weird handshake success.
            }

            if (json.containsKey("anonymous_user_count")) {
                int anonymousCount = json.getNumber("anonymous_user_count").intValue();

                // Oh yeah, pure jank.
                if (this.lastAnonymousCount != anonymousCount) {
                    int difference = anonymousCount - this.lastAnonymousCount;

                    if (difference > 0) {
                        for (int i = 0; i != difference; i++) {
                            listener.onJoin(anonymousViewer);
                        }
                    } else {
                        for (int i = 0; i != -difference; i++) {
                            listener.onLeave(anonymousViewer);
                        }
                    }

                    this.lastAnonymousCount = anonymousCount;
                }
            }

            if (json.containsKey("user_event")) {
                JsonObject userEvent = json.getObject("user_event");
                String caid = userEvent.get("caid").getAsString();

                if (userEvent.get("is_viewing").getAsBoolean()) {
                    try {
                        CaffeineUserInfoRequest request = new CaffeineUserInfoRequest().setCAID(caid);
                        CaffeineUser user = request.send();

                        //@formatter:off
                        Viewer viewer = new Viewer(
                            new ViewerDetails(
                                caid, 
                                user.getImageLink(), 
                                user.getBadge(), 
                                user.getUsername()
                            ), 
                            user, 
                            System.currentTimeMillis() 
                        );
                        //@formatter:on

                        this.viewers.put(caid, viewer);

                        if (listener != null) {
                            listener.onJoin(viewer);
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    Viewer viewer = this.viewers.remove(caid);

                    if (listener != null) {
                        listener.onLeave(viewer);
                    }
                }
            }

            if (listener != null) {
                listener.onAnonymousCount(this.lastAnonymousCount);
                listener.onTotalCount(this.viewers.size() + this.lastAnonymousCount);

                List<Viewer> viewersList = new ArrayList<>();

                for (int i = 0; i != lastAnonymousCount; i++) {
                    viewersList.add(anonymousViewer);
                }

                viewersList.addAll(this.viewers.values());

                viewersList.sort((v1, v2) -> {
                    return Long.compare(v1.getJoinedAt(), v2.getJoinedAt());
                });

                listener.onViewerlist(viewersList);
            }
        }

        @Override
        public void onClose() {
            listener.onClose();
        }

    }

}
