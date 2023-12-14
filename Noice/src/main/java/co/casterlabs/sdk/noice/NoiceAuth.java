package co.casterlabs.sdk.noice;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import lombok.NonNull;

public class NoiceAuth extends OAuthProvider {
    private static final OAuthStrategy TOKEN_STRATEGY = new NoiceTokenStrategy();

    public NoiceAuth(@NonNull String refreshToken) throws ApiAuthException {
        super(TOKEN_STRATEGY, "", "", refreshToken);
    }

    @Override
    protected String getTokenEndpoint() {
        return NoiceApiJava.API + "/v4/auth/session/session:refresh";
    }

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

}
