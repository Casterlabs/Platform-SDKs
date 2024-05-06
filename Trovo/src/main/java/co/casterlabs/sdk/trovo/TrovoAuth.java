package co.casterlabs.sdk.trovo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.OAuthProvider;
import co.casterlabs.apiutil.auth.OAuthStrategy;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;
import lombok.SneakyThrows;

public class TrovoAuth extends OAuthProvider {
    private static final OAuthStrategy STRATEGY = new TrovoOAuthStrategy();
    private static final String AUTH_CODE_URL = "https://open-api.trovo.live/openplatform/exchangetoken";
    private static final String REFRESH_URL = "https://open-api.trovo.live/openplatform/refreshtoken";
    private static final String CHAT_TOKEN_URL = "https://open-api.trovo.live/openplatform/chat/token";

    static {
        TrovoApiJava.class.toString(); // Load.
    }

    public TrovoAuth(@NonNull String clientId, @NonNull String clientSecret, @NonNull String refreshToken) throws ApiAuthException {
        super(STRATEGY, clientId, clientSecret, refreshToken);
    }

    public TrovoAuth(@NonNull String clientId) throws ApiAuthException {
        super(clientId);
    }

    @SneakyThrows
    @Override
    protected void authenticateRequest0(@NonNull HttpRequest.Builder request) {
        if (!this.isApplicationAuth()) {
            request.header("Authorization", "OAuth " + this.getAccessToken());
        }
        request.header("Client-ID", this.getClientId());
    }

    @Override
    protected String getTokenEndpoint() {
        return REFRESH_URL;
    }

    public JsonObject getChatToken() throws ApiAuthException, IOException, ApiException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(CHAT_TOKEN_URL)),
            RsonBodyHandler.of(JsonObject.class),
            this
        ).body();
    }

    public static OAuthResponse authorize(String code, String redirectUri, String clientId, String clientSecret) throws IOException, ApiException, ApiAuthException {
        return OAuthProvider.authorize(STRATEGY, AUTH_CODE_URL, code, redirectUri, clientId, clientSecret);
    }

}
