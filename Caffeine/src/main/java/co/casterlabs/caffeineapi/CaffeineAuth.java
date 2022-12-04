package co.casterlabs.caffeineapi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.Request.Builder;
import okhttp3.Response;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Getter
public class CaffeineAuth extends AuthProvider {
    private static final long EXPIRE_TIME = TimeUnit.MINUTES.toMillis(5);

    private static String anonymousCredential;

    private @Getter String refreshToken;
    private long loginTimestamp;

    private @Getter String caid;
    private String accessToken;
    private String signedToken;
    private String credential;

    @SneakyThrows
    public String getAccessToken() {
        if (this.isExpired()) this.refresh();

        return this.accessToken;
    }

    @SneakyThrows
    public String getSignedToken() {
        if (this.isExpired()) this.refresh();

        return this.signedToken;
    }

    @SneakyThrows
    public String getCredential() {
        if (this.isExpired()) this.refresh();

        return this.credential;
    }

    static {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    try (Response response = HttpUtil.sendHttpGet("https://api.caffeine.tv/v1/credentials/anonymous", null)) {
                        JsonObject json = CaffeineApi.RSON.fromJson(response.body().string(), JsonObject.class);

                        anonymousCredential = json.get("credential").getAsString();
                    }

                    Thread.sleep(EXPIRE_TIME);
                } catch (Exception e) {
                    FastLogger.logStatic(LogLevel.SEVERE, "An exception occurred whilst updating the anonymous credential:\n%s", e);
                }
            }
        });

        t.setName("CaffeineApiJava Anonymous Credential Refresh");
        t.setDaemon(true);
        t.start();
    }

    public void login(@NonNull String username, @NonNull String password, @Nullable String twoFactor) throws ApiAuthException, MfaAwaitException {
        JsonObject request = new JsonObject()
            .put(
                "account",
                new JsonObject()
                    .put("username", username)
                    .put("password", password)
            );

        if (twoFactor != null) {
            request.put("mfa", JsonObject.singleton("opt", twoFactor));
        }

        sendAuth(CaffeineEndpoints.SIGNIN, request);
    }

    public void login(@NonNull String refreshToken) throws ApiAuthException {
        JsonObject request = JsonObject.singleton("refresh_token", refreshToken);

        try {
            sendAuth(CaffeineEndpoints.TOKEN, request);
        } catch (MfaAwaitException ignored) {} // Never thrown by this.
    }

    private void sendAuth(String url, JsonObject payload) throws ApiAuthException, MfaAwaitException {
        try (Response authResponse = HttpUtil.sendHttp(payload.toString(), url, null, "application/json")) {
            String body = authResponse.body().string();

            if (authResponse.code() == 401) {
                throw new ApiAuthException(body);
            } else {
                JsonObject json = CaffeineApi.RSON.fromJson(body, JsonObject.class);

                if (json.containsKey("next")) {
                    throw new MfaAwaitException();
                }

                if (!json.containsKey("credentials")) {
                    throw new ApiAuthException("Unknown response: " + body);
                }

                JsonObject credentials = json.getObject("credentials");

                this.refreshToken = credentials.get("refresh_token").getAsString();
                this.loginTimestamp = System.currentTimeMillis();

                this.accessToken = credentials.get("access_token").getAsString();
                this.credential = credentials.get("credential").getAsString();
                this.caid = credentials.get("caid").getAsString();

                Response signedResponse = HttpUtil.sendHttpGet(String.format(CaffeineEndpoints.SIGNED, this.caid), this);
                JsonObject signed = CaffeineApi.RSON.fromJson(signedResponse.body().string(), JsonObject.class);

                this.signedToken = signed.get("token").getAsString();
            }
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

    @Override
    public void refresh() throws ApiAuthException {
        this.login(this.refreshToken);
    }

    @Override
    protected void authenticateRequest0(@NonNull Builder request) {
        request.addHeader("Authorization", "Bearer " + this.accessToken);
        request.addHeader("x-credential", this.credential);
    }

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

    @Override
    public boolean isExpired() {
        return (System.currentTimeMillis() - this.loginTimestamp) > EXPIRE_TIME;
    }

    public static String getAnonymousCredential() {
        while (anonymousCredential == null) { // A (bad) way to wait for the internal thread to init
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }

        return anonymousCredential;
    }

}
