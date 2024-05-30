package co.casterlabs.apiutil.auth;

import java.net.http.HttpRequest;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class AuthProvider<T> {
    protected final @NonNull AuthDataProvider<T> dataProvider;

    /**
     * Only use this if you know what you are doing!
     */
    @Deprecated
    public T data() {
        return this.dataProvider.load();
    }

    /* ------------ */
    /* Requests     */
    /* ------------ */

    public abstract void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException;

    /* ------------ */
    /* Auth         */
    /* ------------ */

    public abstract void refresh() throws ApiAuthException;

    public abstract boolean isExpired();

    public abstract boolean isApplicationAuth();

}
