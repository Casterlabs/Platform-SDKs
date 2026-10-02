package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.x.Xv2Auth;
import co.casterlabs.sdk.x.v2.types.Xv2Broadcast;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class Xv2PublishBroadcastRequest extends AuthenticatedWebRequest<Xv2Broadcast, Xv2Auth> {
    private String userId;
    private String broadcastId;
    private String title;
    private ChatOption chatOption = ChatOption.EVERYONE;
    private boolean shouldNotTweet = false;
    private String locale = null;

    public Xv2PublishBroadcastRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Xv2Broadcast execute() throws ApiException, ApiAuthException, IOException {
        assert this.userId != null : "userId must be set";
        assert this.broadcastId != null : "broadcastId must be set";
        assert this.title != null : "title must be set";

        JsonObject body = new JsonObject()
            .put("state", "PUBLISH")
            .put("title", this.title)
            .put("chat_option", this.chatOption.ordinal())
            .put("should_not_tweet", this.shouldNotTweet);

        if (this.locale != null) {
            body.put("locale", this.locale);
        }

        String url = String.format("https://api.x.com/2/users/%s/broadcasts/%s/state", this.userId, this.broadcastId);

        return _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url))
                .PUT(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            this.auth,
            ResponseHolder.class
        ).broadcast;
    }

    public static enum ChatOption {
        NONE,
        CHAT_DISABLED,
        EVERYONE,
        VERIFIED_ACCOUNTS,
        ACCOUNTS_THE_BROADCASTER_FOLLOWS,
        THE_BROADCASTER_SUBSCRIBERS,
        ACCOUNTS_THE_BROADCASTER_FOLLOWS_2ND_DEGREE, // accounts the broadcaster follows and the accounts they follow
        ;
    }

    @JsonClass(exposeAll = true)
    private static class ResponseHolder {
        public final Xv2Broadcast broadcast = null;

    }

}
