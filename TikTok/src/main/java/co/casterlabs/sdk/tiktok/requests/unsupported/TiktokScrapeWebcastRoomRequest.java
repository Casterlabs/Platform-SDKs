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
import co.casterlabs.sdk.tiktok.TiktokApi;
import co.casterlabs.sdk.tiktok.types.unsupported.TiktokWebcastRoomData;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class TiktokScrapeWebcastRoomRequest extends WebRequest<TiktokWebcastRoomData> {
    // We're looking for the initial hydration data.
    private static final Pattern HYDRATION_DATA = Pattern.compile("<script id=\"SIGI_STATE\" type=\"application\\/json\">.+?<\\/script>");

    private String handle;

    @Override
    protected TiktokWebcastRoomData execute() throws ApiException, ApiAuthException, IOException {
        if (this.handle.startsWith("@")) {
            this.handle = this.handle.substring(1);
        }

        String pageHtml = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(TiktokApi.TIKTOK_WEB_URL + "/@" + this.handle + "/live")),
            BodyHandlers.ofString(),
            null
        ).body();

        Matcher m = HYDRATION_DATA.matcher(pageHtml);
        if (!m.find()) {
            throw new ApiException("Unable to scrape room data from page.");
        }

        String hydrationData = m.group().trim();
        hydrationData = hydrationData.substring(
            hydrationData.indexOf('>') + 1,
            hydrationData.lastIndexOf('<')
        );

        JsonObject json = Rson.DEFAULT.fromJson(hydrationData, JsonObject.class);
        JsonObject currentRoom = json.getObject("CurrentRoom");

        if (!json.containsKey("LiveRoom")) {
            throw new ApiException("No room data available (user has never streamed).");
        }

        JsonObject liveRoom = json.getObject("LiveRoom");

        // Combine a bunch of objects into a single object.
        JsonObject combinedJson = new JsonObject();

        currentRoom.forEach((e) -> combinedJson.put(e.getKey(), e.getValue()));
        liveRoom.forEach((e) -> combinedJson.put(e.getKey(), e.getValue()));

        if (liveRoom.containsKey("liveRoomUserInfo")) {
            json.getObject("LiveRoom").getObject("liveRoomUserInfo").forEach((e) -> combinedJson.put(e.getKey(), e.getValue()));
        }

        // We want THIS loading state.
        combinedJson.put("loadingState", currentRoom.get("loadingState"));

        return Rson.DEFAULT.fromJson(combinedJson, TiktokWebcastRoomData.class);
    }

}
