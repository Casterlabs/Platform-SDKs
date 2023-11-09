package co.casterlabs.sdk.tiktok.unsupported.webcast;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import lombok.NonNull;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebcastCookies extends AuthProvider {
    private List<Cookie> cookies = new LinkedList<>();

    private final OkHttpClient client = new OkHttpClient.Builder()
        .cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> newCookies) {
                cookies = newCookies;
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return cookies;
            }
        })
        .build();

    private String userAgentToUse = "Casterlabs (TiktokApiJava); Like a/boss.";

    public String getCookie() {
        return this.cookies
            .stream()
            .map((c) -> c.toString())
            .collect(Collectors.joining("; "));
    }

    public String sendHttpRequest(@NonNull Request.Builder builder) throws IOException {
        try (Response response = this.client.newCall(
            builder
                .header("User-Agent", this.userAgentToUse)
                .build()
        ).execute()) {
            this.parseTTCookie(response);
            return response.body().string();
        }
    }

    public byte[] sendHttpRequestBytes(@NonNull Request.Builder builder) throws IOException {
        try (Response response = this.client.newCall(
            builder
                .header("User-Agent", this.userAgentToUse)
                .build()
        ).execute()) {
            this.parseTTCookie(response);
            return response.body().bytes();
        }
    }

    private void parseTTCookie(Response response) {
        for (String cookie : response.headers("X-Set-TT-Cookie")) {
            this.cookies.add(
                Cookie.parse(
                    HttpUrl.parse(WebcastConstants.TIKTOK_WEBCAST_URL),
                    cookie
                )
            );
        }
    }

    @Override
    protected void authenticateRequest0(@NonNull Request.Builder request) {
        throw new UnsupportedOperationException("Use WebcastCookies#sendHttpRequest instead.");
    }

    @Override
    public void refresh() throws ApiAuthException {}

    @Override
    public boolean isApplicationAuth() {
        return true;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

}
