package co.casterlabs.sdk.kick.unsupported.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.unsupported.UnsupportedKickApi;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UnsupportedKickLoginRequest extends WebRequest<String> {
    private @Setter String username;
    private @Setter String password;
    private @Setter @Nullable String oneTimePassword;

    /**
     * @throws ApiException with a message of "2FA" if a 2FA/OTP code is needed.
     */
    @Override
    protected String execute() throws ApiException, ApiAuthException, IOException {
        assert this.username != null : "You must specify an email/username to login with.";
        assert this.password != null : "You must specify a password to login with.";

        JsonObject tokenProvider = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/kick-token-provider"))
                .header("Accept", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        // We're using the mobile login method. This seems fine as the mobile app uses
        // the same API as desktop, and I am yet to find an endpoint that the mobile
        // token can't authorize.
        JsonObject loginPayload = new JsonObject()
            .put("isMobileRequest", true)
            .put("email", this.username)
            .put("password", this.password)

            // Token Provider Stuff
            .put(tokenProvider.getString("nameFieldName"), "")
            .put(tokenProvider.getString("validFromFieldName"), tokenProvider.get("encryptedValidFrom"));

        if (this.oneTimePassword != null) {
            loginPayload.put("one_time_password", this.oneTimePassword);
        }

        JsonObject body = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(UnsupportedKickApi.API_BASE_URL + "/mobile/login"))
                .POST(BodyPublishers.ofString(loginPayload.toString()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        if (body.containsKey("2fa_required") && body.getBoolean("2fa_required")) {
            throw new ApiException("2FA");
        }

        if (body.containsKey("message")) {
            throw new ApiException(body.getString("message"));
        }

        return body.getString("token");
    }

}
