package co.casterlabs.apiutil.auth;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.commons.async.AsyncTask;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import okhttp3.Request;

public abstract class OAuthProvider extends AuthProvider {
    protected final Object lock = new Object();

    private @Nullable Consumer<String> onNewRefreshToken;

    private OAuthStrategy strategy;

    /**
     * @deprecated Only used for the Regular constructors. Database-aware will NEVER
     *             use this field.
     */
    @Deprecated
    private String _lastRefreshToken;

    private Supplier<String> refreshTokenSupplier;
    private @Getter String clientId;
    private @Getter String clientSecret;
    private @Getter String redirectUri;

    private String accessToken;
    private String scope;

    private long loginTimestamp = -1;
    private int expiresIn = -1;

    // Regular constructors.
    public OAuthProvider(@NonNull OAuthStrategy strategy, @NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        this(strategy, clientId, clientSecret, refreshToken, "");
    }

    public OAuthProvider(@NonNull OAuthStrategy strategy, @NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken, @NonNull String redirectUri) throws ApiAuthException {
        this.strategy = strategy;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this._lastRefreshToken = refreshToken;
        this.redirectUri = redirectUri;
        this.refreshTokenSupplier = () -> this._lastRefreshToken;
    }

    // Database-aware constructors.

    public OAuthProvider(@NonNull OAuthStrategy strategy, @NonNull String clientId, @NonNull String clientSecret, @NonNull Supplier<String> refreshTokenSupplier) throws ApiAuthException {
        this(strategy, clientId, clientSecret, refreshTokenSupplier, "");
    }

    public OAuthProvider(@NonNull OAuthStrategy strategy, @NonNull String clientId, @NonNull String clientSecret, @NonNull Supplier<String> refreshTokenSupplier, @NonNull String redirectUri) throws ApiAuthException {
        this.strategy = strategy;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshTokenSupplier = refreshTokenSupplier;
        this.redirectUri = redirectUri;
    }

    // Application auth.
    public OAuthProvider(@NonNull String clientId) {
        this.clientId = clientId;
    }

    // Setter for the handler. We use <T> so that implementing classes don't have to
    // override this and return themselves.
    @SuppressWarnings("unchecked")
    public <T extends OAuthProvider> T onNewRefreshToken(@Nullable Consumer<String> handler) {
        this.onNewRefreshToken = handler;
        return (T) this;
    }

    /* These getters will automatically refresh the token if it's invalid. */

    public String getAccessToken() throws ApiAuthException, InterruptedException {
        if (this.isApplicationAuth()) {
            throw new IllegalStateException("Application Auth does not have an access token.");
        }

        if (this.isExpired()) {
            this.refresh();
        }

        return this.accessToken;
    }

    public String getScope() throws ApiAuthException {
        if (this.isApplicationAuth()) {
            throw new IllegalStateException("Application Auth does not have a scope.");
        }

        if (this.isExpired()) {
            this.refresh();
        }

        return this.scope;
    }

    // Internals.

    protected abstract String getTokenEndpoint();

    public final String getRefreshToken() {
        return this.refreshTokenSupplier.get();
    }

    // Override.

    @Override
    protected void authenticateRequest0(@NonNull Request.Builder request) {
        if (!this.isApplicationAuth()) {
            request.addHeader("Authorization", "Bearer " + this.accessToken);
        }
        request.addHeader("Client-ID", this.clientId);
    }

    @Override
    public void refresh() throws ApiAuthException {
        if (this.isApplicationAuth()) {
            return;
        }

        synchronized (this.lock) {
            String proposedRefreshToken = this.getRefreshToken();

            OAuthResponse data = this.strategy.refresh(this.getTokenEndpoint(), proposedRefreshToken, this.redirectUri, this.clientId, this.clientSecret);

            this._lastRefreshToken = data.refreshToken;
            this.accessToken = data.accessToken;
            this.expiresIn = data.expiresIn;
            this.scope = data.scope;
            this.loginTimestamp = System.currentTimeMillis();

            if (!data.refreshToken.equals(proposedRefreshToken)) {
                AsyncTask.createNonDaemon(() -> {
                    if (this.onNewRefreshToken != null) {
                        this.onNewRefreshToken.accept(this._lastRefreshToken);

                    }
                });
            }
        }
    }

    @Override
    public boolean isApplicationAuth() {
        return this.refreshTokenSupplier != null;
    }

    @Override
    public boolean isExpired() {
        if (this.isApplicationAuth()) {
            return false;
        }

        synchronized (this.lock) {
            return ((System.currentTimeMillis() - this.loginTimestamp) / 1000) > this.expiresIn;
        }
    }

    // Helpers.

    protected static OAuthResponse authorize(@NonNull OAuthStrategy strategy, String endpoint, String code, String redirectUri, String clientId, String clientSecret) throws ApiAuthException {
        return strategy.codeGrant(endpoint, code, redirectUri, clientId, clientSecret);
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class OAuthResponse {
        @JsonField("access_token")
        private String accessToken;

        @JsonField("expires_in")
        private int expiresIn;

        @JsonField("refresh_token")
        private String refreshToken;

        private String scope;

        @JsonField("token_type")
        private String tokenType;

    }

}
