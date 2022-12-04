package co.casterlabs.sdk.twitch.helix.webhooks;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.webhooks.HelixGetWebhookSubscriptionsRequest.WebhookSubscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import okhttp3.Response;

public class HelixGetWebhookSubscriptionsRequest extends AuthenticatedWebRequest<List<WebhookSubscription>, TwitchHelixAuth> {

    public HelixGetWebhookSubscriptionsRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    @Override
    public List<WebhookSubscription> execute() throws ApiException, ApiAuthException, IOException {
        List<WebhookSubscription> webhooks = new ArrayList<>();
        String after = "";

        while (after != null) {
            Response response = HttpUtil.sendHttpGet(String.format("https://api.twitch.tv/helix/webhooks/subscriptions?first=100&after=%s", after), null, this.auth);
            JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

            response.close();

            if (response.code() == 200) {
                JsonArray data = json.getArray("data");

                for (JsonElement e : data) {
                    webhooks.add(TwitchApi.RSON.fromJson(e, WebhookSubscription.class));
                }

                JsonObject pagination = json.getObject("pagination");

                if (pagination.containsKey("cursor")) {
                    after = pagination.get("cursor").getAsString();
                } else {
                    after = null;
                }
            } else {
                throw new ApiException("Unable to get subscriptions: " + json.get("message").getAsString());
            }
        }

        return webhooks;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @JsonClass(exposeAll = true)
    public static class WebhookSubscription {
        private @NonNull String topic;
        private @NonNull String callback;
        @JsonField("expires_at")
        private @NonNull Instant expiresAt;

        public void remove(@NonNull TwitchHelixAuth auth) throws ApiException, ApiAuthException, IOException {
            HelixWebhookSubscribeRequest request = new HelixWebhookSubscribeRequest(HelixWebhookSubscribeRequest.WebhookSubscribeMode.UNSUBSCRIBE, this.callback, this.topic, auth);

            request.send();
        }

        public boolean isExpired() {
            return Instant.now().isAfter(this.expiresAt);
        }

    }

}
