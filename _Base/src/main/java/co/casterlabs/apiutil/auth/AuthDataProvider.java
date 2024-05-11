package co.casterlabs.apiutil.auth;

import lombok.AllArgsConstructor;
import lombok.NonNull;

public interface AuthDataProvider<T> {

    public @NonNull T load();

    public void save(@NonNull T data);

    @AllArgsConstructor
    public static class InMemoryAuthDataProvider<T> implements AuthDataProvider<T> {
        private @NonNull T data;

        @Override
        public synchronized @NonNull T load() {
            return this.data;
        }

        @Override
        public synchronized void save(@NonNull T data) {
            this.data = data;
        }
    }

}
