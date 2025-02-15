package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;

public class TwitchHelixCreateEventSubSubscriptionRequest extends AuthenticatedWebRequest<Void, TwitchHelixAuth> {
    private String type;
    private String version;
    private JsonObject condition = new JsonObject();
    private JsonObject transport;

    /**
     * @param auth must be application auth if the subscription is a webhook or
     *             conduit, user auth otherwise.
     */
    public TwitchHelixCreateEventSubSubscriptionRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public TwitchHelixCreateEventSubSubscriptionRequest type(String type, String version) {
        this.type = type;
        this.version = version;
        return this;
    }

    /**
     * Call as many times as you need to build the condition.
     */
    public TwitchHelixCreateEventSubSubscriptionRequest condition(String key, String value) {
        this.condition.put(key, value);
        return this;
    }

    public TwitchHelixCreateEventSubSubscriptionRequest webhookTransport(String callback, String secret) {
        this.transport = new JsonObject()
            .put("method", "webhook")
            .put("callback", callback)
            .put("secret", secret);
        return this;
    }

    public TwitchHelixCreateEventSubSubscriptionRequest websocketTransport(String sessionId) {
        this.transport = new JsonObject()
            .put("method", "websocket")
            .put("session_id", sessionId);
        return this;
    }

    public TwitchHelixCreateEventSubSubscriptionRequest conduitTransport(String conduitId) {
        this.transport = new JsonObject()
            .put("method", "conduit")
            .put("conduit_id", conduitId);
        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.type != null : "You must specify the type of event you want to subscribe to";
        assert this.version != null : "You must specify the version of the event you want to subscribe to";
//        assert this.condition.isEmpty() : "You must specify the condition of the event you want to subscribe to"; // Unclear if this is a valid state.
        assert this.transport != null : "You must specify the transport of the event you want to subscribe to";

        final String url = "https://api.twitch.tv/helix/eventsub/subscriptions";

        JsonObject body = new JsonObject()
            .put("type", this.type)
            .put("version", this.version)
            .put("condition", this.condition)
            .put("transport", this.transport);

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return null;
    }

}
