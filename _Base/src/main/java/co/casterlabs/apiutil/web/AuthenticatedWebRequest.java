package co.casterlabs.apiutil.web;

import co.casterlabs.apiutil.auth.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class AuthenticatedWebRequest<T, A extends AuthProvider> extends WebRequest<T> {
    protected @NonNull A auth;

}
