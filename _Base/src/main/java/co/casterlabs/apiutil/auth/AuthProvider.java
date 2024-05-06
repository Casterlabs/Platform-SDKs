package co.casterlabs.apiutil.auth;

import java.net.http.HttpRequest;

import lombok.NonNull;

public abstract class AuthProvider {

    /* ------------ */
    /* Requests     */
    /* ------------ */

    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        if (this.isExpired()) {
            this.refresh();
        }

        this.authenticateRequest0(request);
    }

    protected abstract void authenticateRequest0(@NonNull HttpRequest.Builder request);

    /* ------------ */
    /* Auth         */
    /* ------------ */

    public abstract void refresh() throws ApiAuthException;

    public abstract boolean isApplicationAuth();

    public abstract boolean isExpired();

}
