package co.casterlabs.apiutil.auth;

import lombok.NonNull;
import okhttp3.Request;

public abstract class AuthProvider {

    /* ------------ */
    /* Requests     */
    /* ------------ */

    public void authenticateRequest(@NonNull Request.Builder request) throws ApiAuthException {
        if (this.isExpired()) {
            this.refresh();
        }

        this.authenticateRequest0(request);
    }

    protected abstract void authenticateRequest0(@NonNull Request.Builder request);

    /* ------------ */
    /* Auth         */
    /* ------------ */

    public abstract void refresh() throws ApiAuthException;

    public abstract boolean isApplicationAuth();

    public abstract boolean isExpired();

}
