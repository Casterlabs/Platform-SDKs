package co.casterlabs.sdk.x;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthDataProvider;
import co.casterlabs.apiutil.auth.AuthDataProvider.InMemoryAuthDataProvider;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.ParsedQuery;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.sdk.x.Xv1Auth.Xv1AuthData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@SuppressWarnings("deprecation")
public class Xv1Auth extends AuthProvider<Xv1AuthData> {
    private static final URI REQUEST_TOKEN_ENDPOINT = URI.create("https://api.x.com/oauth/request_token");
    private static final URI AUTHORIZE_ENDPOINT = URI.create("https://api.x.com/oauth/authorize");
    private static final URI ACCESS_TOKEN_ENDPOINT = URI.create("https://api.x.com/oauth/access_token");

    private final ReentrantLock lock = new ReentrantLock();

    private final @Getter String consumerKey;
    private final String consumerSecret;

    /* ---------------- */
    /* Construction     */
    /* ---------------- */

    protected Xv1Auth(AuthDataProvider<Xv1AuthData> dataProvider, String consumerKey, String consumerSecret) {
        super(dataProvider);
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    /**
     * Useful when you already have a token + token secret from the Twitter/X
     * developer console.
     */
    protected Xv1Auth(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super(new InMemoryAuthDataProvider<>(Xv1AuthData.of(accessToken, accessTokenSecret)));
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    public static Xv1Auth ofUser(AuthDataProvider<Xv1AuthData> dataProvider, String consumerKey, String consumerSecret) {
        return new Xv1Auth(dataProvider, consumerKey, consumerSecret);
    }

    public static Xv1Auth ofUser(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        return new Xv1Auth(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    /* ---------------- */
    /* Impl.            */
    /* ---------------- */

    /**
     * OAuth v1 cannot be signed from a bare HttpRequest.Builder because Java does
     * not expose the builder's method, URI, or body params. Use the overload below.
     */
    @Override
    public void authenticateRequest(@NonNull HttpRequest.Builder request) throws ApiAuthException {
        throw new ApiAuthException("Twitter OAuth v1 requests must be signed with method, URI, and params.");
    }

    /**
     * Sign a normal Twitter/X API request.
     *
     * extraParams must contain any query params and any
     * application/x-www-form-urlencoded body params that are part of the request.
     * If the params are already present in the URI query string, you do not need to
     * repeat them here.
     */
    public void authenticateRequest(@NonNull HttpRequest.Builder request, @NonNull String method, @NonNull URI uri, @Nullable Map<String, String> extraParams) throws ApiAuthException {
        this.lock.lock();
        try {
            Xv1AuthData data = this.data();

            if (data.accessToken == null || data.accessTokenSecret == null) {
                throw new ApiAuthException("Twitter auth data is missing an access token or token secret.");
            }

            request.setHeader(
                "Authorization",
                OAuth1.sign(
                    method,
                    uri,
                    extraParams,
                    this.consumerKey,
                    this.consumerSecret,
                    data.accessToken,
                    data.accessTokenSecret,
                    null
                )
            );
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void refresh() throws ApiAuthException {
        // OAuth 1.0a access tokens do not have the OAuth 2 refresh-token model.
    }

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

    @Override
    public boolean isExpired() {
        this.lock.lock();
        try {
            Xv1AuthData data = this.data();
            return data.accessToken == null || data.accessTokenSecret == null;
        } finally {
            this.lock.unlock();
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class Xv1AuthData {
        @JsonField("access_token")
        public String accessToken;

        @JsonField("access_token_secret")
        public String accessTokenSecret;

        @JsonField("user_id")
        public @Nullable String userId;

        @JsonField("screen_name")
        public @Nullable String screenName;

        public static Xv1AuthData of(String accessToken, String accessTokenSecret) {
            Xv1AuthData d = new Xv1AuthData();
            d.accessToken = accessToken;
            d.accessTokenSecret = accessTokenSecret;
            return d;
        }

    }

    /* ---------------- */
    /* User Grant       */
    /* ---------------- */

    /**
     * Step 1: Get a temporary request token and build the URL you should redirect
     * the user to.
     *
     * You must keep the returned requestTokenSecret server-side until the callback,
     * because it is needed to exchange the verifier for the real access token.
     */
    public static TwitterRequestToken startUserGrant(@NonNull String consumerKey, @NonNull String consumerSecret, @NonNull String callbackUrl) throws ApiAuthException {
        Map<String, String> params = Map.of("oauth_callback", callbackUrl);

        String response = formEndpoint(
            REQUEST_TOKEN_ENDPOINT,
            OAuth1.sign(
                "POST",
                REQUEST_TOKEN_ENDPOINT,
                params,
                consumerKey,
                consumerSecret,
                null,
                null,
                params
            )
        );

        Map<String, String> parsed = parseForm(response);

        String requestToken = parsed.get("oauth_token");
        String requestTokenSecret = parsed.get("oauth_token_secret");

        if (requestToken == null || requestTokenSecret == null) {
            throw new ApiAuthException("Twitter did not return a request token: " + response);
        }

        String authorizeUrl = AUTHORIZE_ENDPOINT + "?oauth_token=" + OAuth1.encode(requestToken);

        TwitterRequestToken token = new TwitterRequestToken();
        token.authorizationUrl = authorizeUrl;
        token.requestToken = requestToken;
        token.requestTokenSecret = requestTokenSecret;
        token.callbackConfirmed = Boolean.parseBoolean(parsed.getOrDefault("oauth_callback_confirmed", "false"));
        return token;
    }

    /**
     * Step 2: Send the user to token.authorizationUrl.
     */

    /**
     * Step 3: Exchange the callback query's oauth_token + oauth_verifier for a
     * usable access token.
     */
    public static Xv1AuthData exchangeUserGrant(@NonNull ParsedQuery query, @NonNull String consumerKey, @NonNull String consumerSecret, @NonNull String requestTokenSecret) throws ApiAuthException {
        String callbackToken = query.getSingle("oauth_token");
        String verifier = query.getSingle("oauth_verifier");

        Map<String, String> params = Map.of(
            "oauth_token", callbackToken,
            "oauth_verifier", verifier
        );

        String response = formEndpoint(
            ACCESS_TOKEN_ENDPOINT,
            OAuth1.sign(
                "POST",
                ACCESS_TOKEN_ENDPOINT,
                params,
                consumerKey,
                consumerSecret,
                callbackToken,
                requestTokenSecret,
                params
            )
        );

        Map<String, String> parsed = parseForm(response);

        Xv1AuthData data = new Xv1AuthData();
        data.accessToken = parsed.get("oauth_token");
        data.accessTokenSecret = parsed.get("oauth_token_secret");
        data.userId = parsed.get("user_id");
        data.screenName = parsed.get("screen_name");

        if (data.accessToken == null || data.accessTokenSecret == null) {
            throw new ApiAuthException("Twitter did not return an access token: " + response);
        }

        return data;
    }

    @ToString
    @NoArgsConstructor
    @JsonClass(exposeAll = true)
    public static class TwitterRequestToken {
        @JsonField("authorization_url")
        public String authorizationUrl;

        @JsonField("request_token")
        public String requestToken;

        @JsonField("request_token_secret")
        public String requestTokenSecret;

        @JsonField("callback_confirmed")
        public boolean callbackConfirmed;
    }

    /* ---------------- */
    /* Util             */
    /* ---------------- */

    private static String formEndpoint(URI uri, String authorization) throws ApiAuthException {
        try {
            HttpResponse<String> response = WebRequest.sendHttpRequest(
                HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .POST(BodyPublishers.noBody())
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "identity"),
                HttpResponse.BodyHandlers.ofString(),
                null
            );

            if (response.statusCode() < 200 || response.statusCode() > 299) {
                if (response.statusCode() == 401) {
                    throw new ApiAuthException(response.body());
                } else {
                    throw new ApiException(response.body());
                }
            }

            return response.body();
        } catch (IOException | ApiException e) {
            throw new ApiAuthException(e);
        }
    }

    private static Map<String, String> parseForm(String form) {
        Map<String, String> result = new LinkedHashMap<>();

        if (form == null || form.isBlank()) {
            return result;
        }

        for (String pair : form.split("&")) {
            if (pair.isBlank()) continue;

            int idx = pair.indexOf('=');
            String key = idx == -1 ? pair : pair.substring(0, idx);
            String value = idx == -1 ? "" : pair.substring(idx + 1);

            result.put(
                URLDecoder.decode(key, StandardCharsets.UTF_8),
                URLDecoder.decode(value, StandardCharsets.UTF_8)
            );
        }

        return result;
    }

    private static final class OAuth1 {
        private static final String HMAC_SHA1 = "HmacSHA1";
        private static final String SIGNATURE_METHOD = "HMAC-SHA1";
        private static final String VERSION = "1.0";
        private static final char[] HEX = "0123456789ABCDEF".toCharArray();
        private static final SecureRandom RANDOM = new SecureRandom();

        private static String sign(
            String method,
            URI uri,
            @Nullable Map<String, String> extraParams,
            String consumerKey,
            String consumerSecret,
            @Nullable String token,
            @Nullable String tokenSecret,
            @Nullable Map<String, String> forcedOAuthParams
        ) throws ApiAuthException {
            Map<String, String> oauth = new LinkedHashMap<>();

            if (forcedOAuthParams != null) {
                for (Map.Entry<String, String> e : forcedOAuthParams.entrySet()) {
                    if (e.getKey().startsWith("oauth_")) {
                        oauth.put(e.getKey(), e.getValue());
                    }
                }
            }

            oauth.putIfAbsent("oauth_consumer_key", consumerKey);
            oauth.putIfAbsent("oauth_nonce", nonce());
            oauth.putIfAbsent("oauth_signature_method", SIGNATURE_METHOD);
            oauth.putIfAbsent("oauth_timestamp", Long.toString(Instant.now().getEpochSecond()));
            oauth.putIfAbsent("oauth_version", VERSION);

            if (token != null) {
                oauth.putIfAbsent("oauth_token", token);
            }

            Map<String, String> signatureParams = new LinkedHashMap<>();
            signatureParams.putAll(queryParams(uri));

            if (extraParams != null) {
                signatureParams.putAll(extraParams);
            }

            signatureParams.putAll(oauth);

            String parameterString = parameterString(signatureParams);
            String baseString = method.toUpperCase() + '&' + encode(baseUri(uri)) + '&' + encode(parameterString);
            String signingKey = encode(consumerSecret) + '&' + (tokenSecret == null ? "" : encode(tokenSecret));
            String signature = hmacSha1(signingKey, baseString);

            oauth.put("oauth_signature", signature);

            return "OAuth " + authorizationParams(oauth);
        }

        private static Map<String, String> queryParams(URI uri) {
            Map<String, String> result = new LinkedHashMap<>();
            String query = uri.getRawQuery();

            if (query == null || query.isBlank()) {
                return result;
            }

            for (String pair : query.split("&")) {
                if (pair.isBlank()) continue;

                int idx = pair.indexOf('=');
                String key = idx == -1 ? pair : pair.substring(0, idx);
                String value = idx == -1 ? "" : pair.substring(idx + 1);

                result.put(
                    URLDecoder.decode(key, StandardCharsets.UTF_8),
                    URLDecoder.decode(value, StandardCharsets.UTF_8)
                );
            }

            return result;
        }

        private static String baseUri(URI uri) {
            String scheme = uri.getScheme().toLowerCase();
            String host = uri.getHost().toLowerCase();
            int port = uri.getPort();

            boolean includePort = port != -1 &&
                !(scheme.equals("http") && port == 80) &&
                !(scheme.equals("https") && port == 443);

            return scheme + "://" + host + (includePort ? ":" + port : "") + uri.getRawPath();
        }

        private static String parameterString(Map<String, String> params) {
            List<Map.Entry<String, String>> entries = new ArrayList<>(params.entrySet());

            entries.sort(
                Comparator
                    .comparing((Map.Entry<String, String> e) -> encode(e.getKey()))
                    .thenComparing(e -> encode(e.getValue()))
            );

            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : entries) {
                if (result.length() != 0) {
                    result.append('&');
                }

                result
                    .append(encode(entry.getKey()))
                    .append('=')
                    .append(encode(entry.getValue()));
            }

            return result.toString();
        }

        private static String authorizationParams(Map<String, String> oauth) {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : oauth.entrySet()) {
                if (result.length() != 0) {
                    result.append(", ");
                }

                result
                    .append(encode(entry.getKey()))
                    .append("=\"")
                    .append(encode(entry.getValue()))
                    .append('"');
            }

            return result.toString();
        }

        private static String hmacSha1(String secret, String message) throws ApiAuthException {
            try {
                Mac mac = Mac.getInstance(HMAC_SHA1);
                mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA1));
                return Base64.getEncoder().encodeToString(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                throw new ApiAuthException(e);
            }
        }

        private static String nonce() {
            byte[] bytes = new byte[24];
            RANDOM.nextBytes(bytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        }

        private static String encode(String s) {
            StringBuilder result = new StringBuilder();

            for (byte b : s.getBytes(StandardCharsets.UTF_8)) {
                int c = b & 0xFF;

                if ((c >= 'A' && c <= 'Z') ||
                    (c >= 'a' && c <= 'z') ||
                    (c >= '0' && c <= '9') ||
                    c == '-' ||
                    c == '_' ||
                    c == '.' ||
                    c == '~') {
                    result.append((char) c);
                    continue;
                }

                result.append('%');
                result.append(HEX[(c & 0xF0) >> 4]);
                result.append(HEX[c & 0x0F]);
            }

            return result.toString();
        }

    }

}
