package co.casterlabs.sdk.glimesh;

import java.io.IOException;
import java.net.URI;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.Request.Builder;

public class GlimeshAuth extends OAuthProvider {
    private static final OAuthStrategy STRATEGY = new OAuthStrategy.Default();
    private static final String TOKEN_ENDPOINT = GlimeshApiJava.GLIMESH_API + "/oauth/token";

    public GlimeshAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken, @NonNull String redirectUri) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken, redirectUri);
    }

    public GlimeshAuth(@NonNull String clientId) {
        super(clientId);
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull Builder request) {
        if (this.isApplicationAuth()) {
            request.addHeader("Authorization", "Client-ID " + this.getClientId());
        } else {
            request.addHeader("Authorization", "Bearer " + this.getAccessToken());
        }
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    @SneakyThrows
    public URI getRealtimeUrl() {
        String url;

        if (this.isApplicationAuth()) {
            url = GlimeshApiJava.GLIMESH_REALTIME_API + "&client_id=" + this.getClientId();
        } else {
            url = GlimeshApiJava.GLIMESH_REALTIME_API + "&token=" + this.getAccessToken();
        }

        return new URI(url);
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, TOKEN_ENDPOINT, code, redirectUri, clientId, clientSecret);
    }

}
