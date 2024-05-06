package co.casterlabs.sdk.dlive;

import java.io.IOException;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.NonNull;
import lombok.SneakyThrows;

public class DliveAuth extends OAuthProvider {
    private static final String TOKEN_ENDPOINT = "https://dlive.tv/o/token";
    private static final OAuthStrategy STRATEGY = new DliveOAuthStrategy();
    private static final OAuthStrategy CLIENT_STRATEGY = new DliveClientCredentialsStrategy();

    public DliveAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken, @NonNull String redirectUri) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken, redirectUri);
    }

    public DliveAuth(@NonNull String clientId, @NonNull String clientSecret) throws ApiAuthException {
        super(CLIENT_STRATEGY, clientId, clientSecret, "", "");
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", this.getAccessToken());
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, TOKEN_ENDPOINT, code, redirectUri, clientId, clientSecret);
    }

}
