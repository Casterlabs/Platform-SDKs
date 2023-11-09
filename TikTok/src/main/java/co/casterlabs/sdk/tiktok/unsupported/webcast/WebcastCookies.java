package co.casterlabs.sdk.tiktok.unsupported.webcast;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36";

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

    private Map<String, String> clientParams = new HashMap<>();
    {
        String[] userAgentSplit = USER_AGENT.split("/", 2);

        this.clientParams.put("aid", "1988");
        this.clientParams.put("app_name", "tiktok_web");
        this.clientParams.put("browser_name", userAgentSplit[0]);
        this.clientParams.put("browser_online", "true");
        this.clientParams.put("browser_platform", "Win32"); // TODO Keep up to date with USER_AGENT
        this.clientParams.put("browser_version", userAgentSplit[1]);
        this.clientParams.put("cookie_enabled", "true");
        this.clientParams.put("device_platform", "web");
        this.clientParams.put("focus_state", "true");
        this.clientParams.put("from_page", "user");
        this.clientParams.put("history_len", "4");
        this.clientParams.put("is_fullscreen", "false");
        this.clientParams.put("is_page_visible", "true");
        this.clientParams.put("did_rule", "3");
        this.clientParams.put("fetch_rule", "1");
        this.clientParams.put("last_rtt", "0");
        this.clientParams.put("live_id", "12");
        this.clientParams.put("resp_content_type", "protobuf");
        this.clientParams.put("screen_height", "1152");
        this.clientParams.put("screen_width", "2048");
        this.clientParams.put("tz_name", "Europe/London"); // UTC.
        this.clientParams.put("referer", "https,//www.tiktok.com/");
        this.clientParams.put("root_referer", "https,//www.tiktok.com/");
        this.clientParams.put("msToken", "");
        this.clientParams.put("version_code", "180800");
        this.clientParams.put("webcast_sdk_version", "1.3.0");
        this.clientParams.put("update_version_code", "1.3.0");
        this.setLanguage("en-GB");
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

    public WebcastCookies setLanguage(@NonNull String language) {
        this.clientParams.put("app_language", language);
        this.clientParams.put("webcast_language", language);
        this.clientParams.put("browser_language", language.split("_")[0]);
        return this;
    }

    @SuppressWarnings("deprecation")
    public String getClientParams() {
        return this.clientParams
            .entrySet()
            .stream()
            .map((entry) -> URLEncoder.encode(entry.getKey()) + '=' + URLEncoder.encode(entry.getValue()))
            .collect(Collectors.joining("&"));
    }

    public String getCookie() {
        return this.cookies
            .stream()
            .map((c) -> c.toString())
            .collect(Collectors.joining("; "));
    }

    public String sendHttpRequest(@NonNull Request.Builder builder) throws IOException {
        try (Response response = this.client.newCall(
            builder
                .header("User-Agent", USER_AGENT)
                .build()
        ).execute()) {
            this.parseTTCookie(response);
            return response.body().string();
        }
    }

    public byte[] sendHttpRequestBytes(@NonNull Request.Builder builder) throws IOException {
        try (Response response = this.client.newCall(
            builder
                .header("User-Agent", USER_AGENT)
                .build()
        ).execute()) {
            this.parseTTCookie(response);
            return response.body().bytes();
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