package co.casterlabs.brimeapijava;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.NonNull;

public class BrimeAuth extends OAuthProvider {
    private static final OAuthStrategy STRATEGY = new BrimeOAuthStrategy();
    private static final String TOKEN_ENDPOINT = "https://auth.brime.tv/oauth/token";

    public BrimeAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken);
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, TOKEN_ENDPOINT, code, redirectUri, clientId, clientSecret);
    }

}
