package co.casterlabs.sdk.livespace;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.ApiException;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.Request;

public class LivespaceAuth extends OAuthProvider {
    public static String userAgent = "Casterlabs SDK Test";

    private static final OAuthStrategy STRATEGY = new LivespaceOAuthStrategy();

    public LivespaceAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken, @NonNull String redirectUri) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken, redirectUri);
    }

    public LivespaceAuth(@NonNull String clientId) throws ApiAuthException {
        super(clientId);
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull Request.Builder request) {
        if (this.isApplicationAuth()) {
            request.addHeader("Authorization", "Apikey " + this.getClientId());
        } else {
            request.addHeader("Authorization", "Bearer " + this.getAccessToken());
        }
        request.addHeader("User-Agent", userAgent);
    }

    @Override
    protected String getTokenEndpoint() {
        return "";
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, "", code, redirectUri, clientId, clientSecret);
    }

}
