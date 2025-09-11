package co.casterlabs.sdk.tiktok.unsupported;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.concurrent.ThreadLocalRandom;

import co.casterlabs.apiutil.web.QueryBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

// This class is based off of: https://github.com/zerodytrash/TikTok-Live-Connector/blob/1f0b08c955b81d4fdb8be7fb7519abc2ba723a6f/src/lib/config.ts#L207

@Setter
@Getter
@NonNull
@Accessors(chain = true, fluent = true)
public class TiktokWebSession {
    private String webUrl = "https://www.tiktok.com";
    private String webcastUrl = "https://webcast.tiktok.com/webcast";

    private Location location = _Defaults.randomLocation();
    private Screen screen = _Defaults.randomScreen();
    private Device device = _Defaults.randomDevice();
    private String deviceId = generateDeviceId();

    private @Setter(AccessLevel.NONE) String sessionIdCookie;
    private @Setter(AccessLevel.NONE) String targetIdcCookie;

    public TiktokWebSession authentication(@NonNull String sessionIdCookie, @NonNull String targetIdcCookie) {
        this.sessionIdCookie = sessionIdCookie;
        this.targetIdcCookie = targetIdcCookie;
        return this;
    }

    public QueryBuilder httpQuery() {
        return new QueryBuilder()
            .put("aid", "1988")
            .put("app_language", this.location.lang)
            .put("app_name", "tiktok_web")
            .put("browser_language", this.location.langCountry)
            .put("browser_name", this.device.browserName)
            .put("browser_online", "true")
            .put("browser_platform", this.device.browserPlatform)
            .put("browser_version", this.device.browserVersion)
            .put("cookie_enabled", "true")
            .put("device_platform", "web_pc")
            .put("focus_state", "true")
            .put("from_page", "user")
            .put("history_len", "10")
            .put("is_fullscreen", "false")
            .put("is_page_visible", "true")
            .put("screen_height", this.screen.height)
            .put("screen_width", this.screen.width)
            .put("tz_name", this.location.timezone)
            .put("referer", "https://www.tiktok.com/")
            .put("root_referer", "https://www.tiktok.com/")
            .put("channel", "tiktok_web")
            .put("data_collection_enabled", "true")
            .put("os", this.device.os)
            .put("priority_region", this.location.country)
            .put("region", this.location.country)
            .put("user_is_login", "true")
            .put("webcast_language", this.location.lang)
            .put("device_id", this.deviceId);
    }

    public QueryBuilder wsQuery() {
        return new QueryBuilder()
//            .put("version_code", 180800) // ?
            .put("version_code", 270000)
            .put("aid", 1988)
            .put("app_language", this.location.lang)
            .put("app_name", "tiktok_web")
            .put("browser_platform", this.device.browserPlatform)
            .put("browser_language", this.location.langCountry)
            .put("browser_name", this.device.browserName)
            .put("browser_version", this.device.browserVersion)
            .put("browser_online", "true")
            .put("cookie_enabled", "true")
            .put("tz_name", this.location.timezone)
            .put("device_platform", "web")
//            .put("debug", "false")
//            .put("host", "https://webcast.tiktok.com")
            .put("identity", "audience")
            .put("live_id", "12")
            .put("sup_ws_ds_opt", "1")
            .put("update_version_code", "2.0.0")
            .put("did_rule", "3")
            .put("screen_height", this.screen.height)
            .put("screen_width", this.screen.width)
            .put("heartbeat_duration", "0")
            .put("resp_content_type", "protobuf")
            .put("history_comment_count", "6")
            .put("client_enter", "1")
            .put("last_rtt", generateLastRTT())
            .put("ws_direct", 0)
            .put("webcast_language", this.location.lang);
    }

    public HttpRequest.Builder createRequest(String url) {
        return HttpRequest.newBuilder(URI.create(url))
//            .header("Connection", "keep-alive")
            .header("Cache-Control", "max-age=0")
            .header("User-Agent", this.device.userAgent)
            .header("Accept", "text/html,application/json,application/protobuf")
            .header("Referer", "https://www.tiktok.com/")
            .header("Origin", "https://www.tiktok.com")
            .header("Accept-Language", String.format("%s,%s;q=0.9", this.location.langCountry, this.location.lang))
//            .header("Accept-Encoding", "gzip, deflate")
            .header("Sec-Fetch-Site", "same-site")
            .header("Sec-Fetch-Mode", "cors")
            .header("Sec-Fetch-Dest", "empty")
            .header("Sec-Fetch-Ua-Mobile", "?0");
    }

    /* -------------------- */
    /*         Utils        */
    /* -------------------- */

    public static String generateDeviceId() {
        // https://github.com/zerodytrash/TikTok-Live-Connector/blob/ts-rewrite/src/lib/utilities.ts#L116
        // Spec: 19 digit number.
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            result.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return result.toString();
    }

    public static double generateLastRTT() {
        return Math.random() * 100 + 100;
    }

    /* -------------------- */
    /*         Types        */
    /* -------------------- */

    @NonNull
    @AllArgsConstructor
    public static class Location {
        public final String langCountry;
        public final String lang;
        public final String country;
        public final String timezone;
    }

    @NonNull
    @AllArgsConstructor
    public static class Screen {
        public final int width;
        public final int height;
    }

    @NonNull
    @AllArgsConstructor
    public static class Device {
        public final String userAgent;
        public final String browserName;
        public final String browserVersion;
        public final String browserPlatform; // usually MacIntel or Win32
        public final String os; // usually mac or windows

        public Device(String userAgent) {
            // https://github.com/zerodytrash/TikTok-Live-Connector/blob/ts-rewrite/src/lib/utilities.ts#L102
            this.userAgent = userAgent;

            int firstSlash = userAgent.indexOf('/');
            String browserName = userAgent.substring(0, firstSlash);
            String browserVersion = userAgent.substring(firstSlash + 1);

            this.browserName = browserName;
            this.browserVersion = browserVersion;
            this.browserPlatform = userAgent.contains("Macintosh") ? "MacIntel" : "Win32";
            this.os = userAgent.contains("Macintosh") ? "mac" : "windows";
        }

    }

}
