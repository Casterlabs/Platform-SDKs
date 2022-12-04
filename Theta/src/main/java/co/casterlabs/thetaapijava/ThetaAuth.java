package co.casterlabs.thetaapijava;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.Request.Builder;

public class ThetaAuth extends OAuthProvider {
    private static final String TOKEN_ENDPOINT = "https://api.theta.tv/v1/oauth/token";
    private static final OAuthStrategy STRATEGY = new ThetaOAuthStrategy();

    @Getter
    private String clientSecret;

    public ThetaAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken);
        this.clientSecret = clientSecret;
    }

    public ThetaAuth(@NonNull String clientId, @NonNull String clientSecret) throws ApiAuthException {
        super(clientId);
        this.clientSecret = clientSecret;
    }

    public String getUserId() throws ApiAuthException {
        // We hijack the response, since Theta does not return the scopes we use that
        // field for passing in the user id. We can't get this any other way.
        return super.getScope();
    }

    @Override
    public String getScope() throws ApiAuthException {
        return null;
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull Builder request) {
        if (this.isApplicationAuth()) {
            String url = request.getUrl$okhttp().toString();

            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }

            url += String.format("client_id=%s&client_secret=%s", this.getClientId(), this.getClientSecret());

            request.url(url);
        } else {
            request.addHeader("x-auth-user", this.getUserId());
            request.addHeader("x-auth-token", this.getAccessToken());
            request.addHeader("Authorization", "Bearer " + this.getAccessToken());
        }
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    public static OAuthResponse authorize(String code, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, TOKEN_ENDPOINT, code, null, clientId, clientSecret);
    }

}
