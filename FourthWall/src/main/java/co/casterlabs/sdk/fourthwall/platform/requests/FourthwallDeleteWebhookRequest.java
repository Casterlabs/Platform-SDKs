package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallDeleteWebhookRequest extends AuthenticatedWebRequest<Void, FourthwallAuth> {
    private @NonNull String webhookId;

    public FourthwallDeleteWebhookRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.webhookId != null : "You must specify the Webhook ID to check.";

        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create("https://api.fourthwall.com/open-api/v1.0/webhooks/" + this.webhookId))
                .DELETE(),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        );

        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new ApiException(response.body().toString());
        }

        return null;
    }

}
