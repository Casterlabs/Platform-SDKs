package co.casterlabs.sdk.tiktok.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.tiktok.types.TiktokUserInfo;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Avoid using this unless you absolutely need their @handle.
 */
@Setter
@Deprecated
@Accessors(chain = true)
public class TiktokGetUserHandleRequest extends WebRequest<String> {
    private @NonNull TiktokUserInfo userInfo;

    @Override
    protected String execute() throws ApiException, IOException {
        assert this.userInfo != null : "You must setUserInfo()";

        String startAt = this.userInfo.getProfileDeepLink();

        // We need to wait a bit for the shortened url to hit the correct database.
        // Normally, you wouldn't need to do this.
        long elapsed = System.currentTimeMillis() - this.userInfo.getRequestMadeAt();
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

    private static HttpResponse<String> request(@NonNull String url) throws IOException {
        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(url)),
            BodyHandlers.ofString(),
            null
        );
    }

}
