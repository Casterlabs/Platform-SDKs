package co.casterlabs.sdk.tiktok.requests.unsupported;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.types.unsupported.TiktokScrapedProfileData;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class TiktokScrapeProfileRequest extends WebRequest<TiktokScrapedProfileData> {
    // We're looking for the initial hydration data.
    private static final Pattern HYDRATION_DATA = Pattern.compile("<script id=\"__UNIVERSAL_DATA_FOR_REHYDRATION__\" type=\"application\\/json\">.+?<\\/script>");

    private String handle;

    @Override
    protected TiktokScrapedProfileData execute() throws ApiException, ApiAuthException, IOException {
        if (this.handle.startsWith("@")) {
            this.handle = this.handle.substring(1);
        }

        String pageHtml = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create("https://www.tiktok.com/@" + this.handle)),
            BodyHandlers.ofString(),
            null
        ).body();

        Matcher m = HYDRATION_DATA.matcher(pageHtml);
        if (!m.find()) {
            throw new ApiException("Unable to scrape profile data from page.");
        }

        String hydrationData = m.group().trim();
        hydrationData = hydrationData.substring(
            hydrationData.indexOf('>') + 1,
            hydrationData.lastIndexOf('<')
        );

        JsonObject userInfo = Rson.DEFAULT.fromJson(hydrationData, JsonObject.class)
            .getObject("__DEFAULT_SCOPE__")
            .getObject("webapp.user-detail")
            .getObject("userInfo");

        // Combine a bunch of objects into a single object.
        JsonObject combinedJson = new JsonObject();
        userInfo.getObject("user").forEach((e) -> combinedJson.put(e.getKey(), e.getValue()));
        userInfo.getObject("stats").forEach((e) -> combinedJson.put(e.getKey(), e.getValue()));

        return Rson.DEFAULT.fromJson(combinedJson, TiktokScrapedProfileData.class);
    }

}
