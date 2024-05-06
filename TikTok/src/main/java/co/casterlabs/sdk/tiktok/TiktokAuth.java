package co.casterlabs.sdk.tiktok;

import java.io.IOException;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.NonNull;
import lombok.SneakyThrows;

public class TiktokAuth extends OAuthProvider {
    private static final String TOKEN_ENDPOINT = "https://www.tiktok.com/v2/auth/authorize";
    private static final OAuthStrategy STRATEGY = new TiktokOAuthStrategy();

    public TiktokAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken, "");
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", "Bearer " + this.getAccessToken());
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, TOKEN_ENDPOINT, code, redirectUri, clientId, clientSecret);
    }

}
