package co.casterlabs.sdk.caffeine.realtime.viewers;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.types.UserBadge;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Setter;
import lombok.SneakyThrows;

@Deprecated
public class CaffeineViewersNewFormat implements Closeable {
    // Client type web, as for some reason this is the only way to bring in the
    // newer viewers format
    private static final String AUTH_LOGIN_HEADER = "{\"Headers\":{\"Authorization\":\"Bearer %s\",\"X-Client-Type\":\"web\"},\"Body\":\"{\\\"user\\\":\\\"%s\\\"}\"}";
    private static final long CAFFEINE_KEEPALIVE = TimeUnit.SECONDS.toMillis(15);

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

    private CaffeineAuth auth;
    private Connection conn;

    private @Setter @Nullable CaffeineViewersListener listener;

    public CaffeineViewersNewFormat(CaffeineAuth auth) {
        this.auth = auth;

        try {
            this.conn = new Connection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        if (this.conn.getReadyState() == ReadyState.NOT_YET_CONNECTED) {
            this.conn.connect();
        } else {
            this.conn.reconnect();
        }
    }

    public void connectBlocking() throws InterruptedException {
        if (this.conn.getReadyState() == ReadyState.NOT_YET_CONNECTED) {
            this.conn.connectBlocking();
        } else {
            this.conn.reconnectBlocking();
        }
    }

    public void disconnect() {
        this.conn.close();
    }

    public void disconnectBlocking() throws InterruptedException {
        this.conn.closeBlocking();
    }

    private class Connection extends WebSocketClient {
        private Map<String, Viewer> viewers = new HashMap<>();
        private int lastAnonymousCount = 0;

        public Connection() throws URISyntaxException {
            super(new URI(String.format(CaffeineEndpoints.VIEWERS, auth.getCaid().substring(4))));
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            this.send(String.format(AUTH_LOGIN_HEADER, auth.getAccessToken(), auth.getSignedToken()));

            Thread t = new Thread(() -> {
                while (this.isOpen()) {
                    try {
                        this.send("\"HEALZ\"");
                        Thread.sleep(CAFFEINE_KEEPALIVE);
                    } catch (Exception ignored) {}
                }
            });

            t.setName("CaffeineMessages KeepAlive");
            t.start();

            if (listener != null) {
                listener.onOpen();
            }
        }

        @SneakyThrows
        @Override
        public void onMessage(String raw) {
            if (!raw.equals("\"THANKS\"") && (listener != null)) {
                JsonObject json = CaffeineApi.RSON.fromJson(raw, JsonObject.class);

                if (!json.containsKey("Compatibility-Mode")) {
                    if (json.get("departed_viewer_caids").isJsonArray()) {
                        JsonArray departed = json.getArray("departed_viewer_caids");

                        for (JsonElement e : departed) {
                            Viewer viewer = this.viewers.remove(e.getAsString());

                            if ((viewer != null) && (listener != null)) {
                                listener.onLeave(viewer);
                            }
                        }
                    }

                    if (json.get("new_viewers").isJsonArray()) {
                        JsonArray newViewers = json.getArray("new_viewers");

                        for (JsonElement e : newViewers) {
                            JsonObject newViewer = e.getAsObject();
                            JsonObject userDetails = newViewer.getObject("user_details");

                            JsonElement badgeElement = userDetails.get("badge");

                            String imageLink = CaffeineEndpoints.IMAGES + userDetails.get("avatar_image_path").getAsString();
                            String username = userDetails.get("username").getAsString();
                            UserBadge badge = UserBadge.from(badgeElement.isJsonNull() ? null : badgeElement.getAsString());
                            String caid = userDetails.get("caid").getAsString();

                            ViewerDetails details = new ViewerDetails(caid, imageLink, badge, username);
                            Viewer viewer = new Viewer(details, null, newViewer.getNumber("joined_at").longValue());

                            this.viewers.put(caid, viewer);

                            if (listener != null) {
                                listener.onJoin(viewer);
                            }
                        }
                    }

                    if (!json.get("replacement_viewers").isJsonNull()) {
                        // TODO ?
                        System.out.printf("Replacement viewers: \n%s\n", json.get("replacement_viewers"));
                    }

                    if (listener != null) {
                        JsonObject viewerCounts = json.getObject("viewer_counts");
                        int anonymousCount = viewerCounts.getNumber("anonymous").intValue();

                        listener.onAnonymousCount(anonymousCount);
                        listener.onTotalCount(viewerCounts.getNumber("total").intValue());

                        List<Viewer> viewersList = new ArrayList<>();

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

                        for (int i = 0; i != anonymousCount; i++) {
                            viewersList.add(anonymousViewer);
                        }

                        viewersList.addAll(this.viewers.values());

                        viewersList.sort((v1, v2) -> {
                            return (v1.getJoinedAt() > v2.getJoinedAt()) ? 1 : -1;
                        });

                        listener.onViewerlist(viewersList);
                    }
                }
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            if (listener != null) {
                listener.onClose();
            }
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        try {
            this.disconnectBlocking();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }

}
