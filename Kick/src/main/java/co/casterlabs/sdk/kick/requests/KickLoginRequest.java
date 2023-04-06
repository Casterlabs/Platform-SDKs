package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.kick.KickApi;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Accessors(chain = true)
public class KickLoginRequest extends WebRequest<String> {
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

        JsonObject tokenProvider = Rson.DEFAULT.fromJson(
            WebRequest.sendHttpRequest(
                new Request.Builder()
                    .url(KickApi.API_BASE_URL + "/kick-token-provider")
                    .header("Accept", "application/json"),
                null
            ),
            JsonObject.class
        );

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

        try (
            Response response = client
                .newCall(
                    new Request.Builder()
                        .url(KickApi.API_BASE_URL + "/mobile/login")
                        .post(RequestBody.create(loginPayload.toString().getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json")))
                        .header("Accept", "application/json")
                        .build()
                ).execute()) {
            JsonObject body = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

            if (body.containsKey("2fa_required") && body.getBoolean("2fa_required")) {
                throw new ApiException("2FA");
            }

            if (body.containsKey("message")) {
                throw new ApiException(body.getString("message"));
            }

            return body.getString("token");
        }
    }

}
