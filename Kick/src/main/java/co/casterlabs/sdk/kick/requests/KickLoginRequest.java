package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.net.URLDecoder;
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
                    .url(KickApi.API_BASE_URL + "/kick-token-provider"),
                null
            ),
            JsonObject.class
        );

        JsonObject loginPayload = new JsonObject()
            .put("email", this.username)
            .put("password", this.password)
            .put("one_time_password", this.oneTimePassword)

            // Token Provider Stuff
            .put(tokenProvider.getString("nameFieldName"), "")
            .put(tokenProvider.getString("validFromFieldName"), tokenProvider.get("encryptedValidFrom"));

        try (
            Response response = client
                .newCall(
                    new Request.Builder()
                        .url(KickApi.API_BASE_URL + "/login")
                        .post(RequestBody.create(loginPayload.toString().getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")))
                        .build()
                ).execute()) {
            if (response.code() != 204) {
                String responseBody = response.body().string();
                if (responseBody.contains("2fa_required")) {
                    throw new ApiException("2FA");
                }

                throw new ApiAuthException(responseBody);
            }

            // Login successful, get the cookies.
            for (String setCookieHeader : response.headers("Set-Cookie")) {
                // We're looking for "kick_session-.......;"
                if (!setCookieHeader.startsWith("kick_session=")) continue;

                String token = setCookieHeader.substring("kick_session=".length(), setCookieHeader.indexOf(';'));
                token = URLDecoder.decode(token, "UTF-8"); // Will be url-encoded.

                return token;
            }

            throw new ApiException("No cookie header returned that matches what we're looking for.");
        }
    }

}
