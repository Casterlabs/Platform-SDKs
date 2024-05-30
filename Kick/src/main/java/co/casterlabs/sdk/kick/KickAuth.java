package co.casterlabs.sdk.kick;

import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.sdk.kick.KickAuth.KickAuthData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class KickAuth extends AuthProvider<KickAuthData> {

    public KickAuth(AuthDataProvider<KickAuthData> dataProvider) {
        super(dataProvider);
    }

    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        request.header("Authorization", "Bearer " + this.data().token);
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

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @JsonClass(exposeAll = true)
    public static class KickAuthData {
        public String token;

        @JsonDeserializationMethod("refreshToken")
        private void $deserialize_refreshToken(JsonElement e) {
            this.token = e.getAsString(); // Compat. for some old code.
        }

    }

}
