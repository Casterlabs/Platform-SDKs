package co.casterlabs.sdk.kick;

import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class KickAuth extends AuthProvider {
    private @NonNull String token;

    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", "Bearer " + this.token);
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
