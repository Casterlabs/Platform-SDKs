package co.casterlabs.tiktokapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.tiktokapijava.types.TiktokUserInfo;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Avoid using this unless you absolutely need their @handle.
 */
@Setter
@Deprecated
@Accessors(chain = true)
public class TiktokGetUserHandleRequest extends WebRequest<String> {
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .followRedirects(false)
        .build();

    private @NonNull TiktokUserInfo userInfo;

    @Override
    protected String execute() throws ApiException, IOException {
        assert this.userInfo != null : "You must setUserInfo()";

        String startAt = this.userInfo.getProfileDeepLink();

        // We need to wait a bit for the shortened url to hit the correct database.
        // Normally, you wouldn't need to do this.
        long elapsed = System.currentTimeMillis() - this.userInfo.getRequestMadeAt();
        long waitFor = 5000 - elapsed;
        if (waitFor > 0) {
            try {
                Thread.sleep(waitFor);
            } catch (InterruptedException ignored) {}
        }

        // The first redirect is a de-shortener.
        // It brings us to openapi.tiktok.com.
        try (Response initialRedirect = request(startAt)) {
            String initialTarget = initialRedirect.header("Location");

            if ((initialTarget != null) && initialTarget.startsWith("https://open-api.tiktok.com")) {

                // This redirect brings us to the user's true profile,
                // which we then pull the @ from.
                try (Response trueRedirect = request(initialTarget)) {
                    String trueTarget = trueRedirect.header("Location");

                    int startIdx = trueTarget.indexOf("tiktok.com/@");
                    int endIdx = trueTarget.indexOf('?');

                    if (startIdx != -1) {
                        return trueTarget.substring(startIdx + "tiktok.com/@".length(), endIdx);
                    }
                }
            }
        }

        throw new ApiException("Unable to scrape the TikTok @handle.");
    }

    private static Response request(@NonNull String url) throws IOException {
        return client.newCall(
            new Request.Builder()
                .url(url)
                .build()
        ).execute();
    }

}
