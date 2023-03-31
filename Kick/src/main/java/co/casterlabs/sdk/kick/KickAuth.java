package co.casterlabs.sdk.kick;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import okhttp3.Request;

@AllArgsConstructor
public class KickAuth extends AuthProvider {
    private @NonNull String token;

    @Override
    protected void authenticateRequest0(@NonNull Request.Builder request) {
        request.addHeader("Authorization", "Bearer " + this.token);
    }

    @Override
    public void refresh() throws ApiAuthException {} // NOOP

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

}
