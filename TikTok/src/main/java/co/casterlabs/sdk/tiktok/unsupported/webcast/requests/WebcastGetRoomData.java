package co.casterlabs.sdk.tiktok.unsupported.webcast.requests;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastConstants;
import co.casterlabs.sdk.tiktok.unsupported.webcast.types.WebcastRoomData;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Setter
@Accessors(chain = true)
public class WebcastGetRoomData extends WebRequest<WebcastRoomData> {
    // We're looking for the initial hydration data.
    private static final Pattern HYDRATION_DATA = Pattern.compile("<script id=\"SIGI_STATE\" type=\"application\\/json\">.+?<\\/script>");

    private String handle;

    @Override
    protected WebcastRoomData execute() throws ApiException, ApiAuthException, IOException {
        if (this.handle.startsWith("@")) {
            this.handle = this.handle.substring(1);
        }

        String pageHtml = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(WebcastConstants.TIKTOK_SITE_URL + "/@" + this.handle + "/live"),
            null
        );

        Matcher m = HYDRATION_DATA.matcher(pageHtml);
        if (!m.find()) {
            throw new ApiException("Unable to scrape Room data from page.");
        }

        String hydrationData = m.group().trim();
        hydrationData = hydrationData.substring(
            hydrationData.indexOf('>') + 1,
            hydrationData.lastIndexOf('<')
        );

        JsonObject json = Rson.DEFAULT.fromJson(hydrationData, JsonObject.class);
        JsonObject currentRoom = json.getObject("CurrentRoom");
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

        return Rson.DEFAULT.fromJson(combinedJson, WebcastRoomData.class);
    }

}
