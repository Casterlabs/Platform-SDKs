package co.casterlabs.sdk.twitch.helix;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class TwitchHelixAuth extends OAuthProvider {
    private static final OAuthStrategy CRED_STRATEGY = new ClientCredentialsStrategy();
    private static final OAuthStrategy TOKEN_STRATEGY = new RefreshTokenStrategy();

    private static final String TOKEN_ENDPOINT = "https://id.twitch.tv/oauth2/token";

    public TwitchHelixAuth(@NonNull String clientId, @NonNull String clientSecret) throws ApiAuthException {
        super(CRED_STRATEGY, clientId, clientSecret, "CLIENT_CREDENTIALS");
    }

    public TwitchHelixAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        super(TOKEN_STRATEGY, clientId, clientSecret, refreshToken);
    }

    @Override
    protected String getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    @Override
    public boolean isApplicationAuth() {
        // We always return false so that we can refresh the client credentials auth.
        return false;
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiAuthException {
        return OAuthProvider.authorize(TOKEN_STRATEGY, TOKEN_ENDPOINT, code, redirectUri, clientId, clientSecret);
    }

}
