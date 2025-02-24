package co.casterlabs.sdk.tiktok.requests.unsupported;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.tiktok.TiktokApi;
import co.casterlabs.sdk.tiktok.types.TiktokUserInfo;
import lombok.NonNull;

/**
 * Avoid using this unless you absolutely need their @handle.
 */
@Deprecated
public class TiktokGetUserHandleRequest extends WebRequest<String> {
    private @NonNull String profileDeepLink;
    private long deepLinkRequestedAt;

    public TiktokGetUserHandleRequest byUserInfo(@NonNull TiktokUserInfo userInfo) {
        this.profileDeepLink = userInfo.profileDeepLink;
        this.deepLinkRequestedAt = userInfo.requestMadeAt;
        return this;
    }

    public TiktokGetUserHandleRequest byProfileDeepLink(@NonNull String profileDeepLink) {
        this.profileDeepLink = profileDeepLink;
        this.deepLinkRequestedAt = System.currentTimeMillis();
        return this;
    }

    @Override
    protected String execute() throws ApiException, IOException {
        assert this.profileDeepLink != null : "You must setUserInfo() or setProfileDeepLink()";

        String startAt = profileDeepLink;

        // We need to wait a bit for the shortened url to hit the correct database.
        // Normally, you wouldn't need to do this.
        long elapsed = System.currentTimeMillis() - this.deepLinkRequestedAt;
        long waitFor = 8000 - elapsed;
        if (waitFor > 0) {
            try {
                Thread.sleep(waitFor);
            } catch (InterruptedException ignored) {}
        }

        // The first redirect is a de-shortener.
        // It brings us to openapi.tiktok.com.
        HttpResponse<String> initialRedirect = request(startAt);
        String initialTarget = initialRedirect.headers().firstValue("Location").get();

        if ((initialTarget == null) || !initialTarget.startsWith("https://open-api.tiktok.com")) {
            throw new ApiException("Unable to scrape the TikTok @handle (no initial target)");
        }

        // This redirect brings us to the user's true profile,
        // which we then pull the @ from.
        HttpResponse<String> trueRedirect = request(initialTarget);
        String trueTarget = trueRedirect.headers().firstValue("Location").get();

        int startIdx = trueTarget.indexOf("tiktok.com/@");
        int endIdx = trueTarget.indexOf('?');

        if (startIdx == -1) {
            throw new ApiException("Unable to scrape the TikTok @handle (no secondary target)");
        }

        return trueTarget.substring(startIdx + "tiktok.com/@".length(), endIdx);
    }

    private static final String DEFAULT_TIKTOK_DEEPLINK_URL = "https://vm.tiktok.com";
    private static final String DEFAULT_TIKTOK_OPENAPI2_URL = "https://open-api.tiktok.com";

    private static HttpResponse<String> request(@NonNull String url) throws IOException {
        String rewrittenUrl = url
            .replace(DEFAULT_TIKTOK_DEEPLINK_URL, TiktokApi.TIKTOK_DEEPLINK_URL)
            .replace(DEFAULT_TIKTOK_OPENAPI2_URL, TiktokApi.TIKTOK_OPENAPI2_URL);

//        System.out.printf("%s became %s\n", url, rewrittenUrl);

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(rewrittenUrl)),
            BodyHandlers.ofString(),
            null
        );
    }

}
