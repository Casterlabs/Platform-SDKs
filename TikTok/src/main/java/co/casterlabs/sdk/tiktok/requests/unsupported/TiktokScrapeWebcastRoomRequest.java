package co.casterlabs.sdk.tiktok.requests.unsupported;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ThreadLocalRandom;
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
@Accessors(chain = true, fluent = true)
public class TiktokScrapeWebcastRoomRequest extends WebRequest<TiktokWebcastRoomData> {
    // We're looking for the initial hydration data.
    private static final Pattern HYDRATION_DATA = Pattern.compile("<script id=\"SIGI_STATE\" type=\"application\\/json\">.+?<\\/script>");
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36";

    private String byHandle;

    @Override
    protected TiktokWebcastRoomData execute() throws ApiException, ApiAuthException, IOException {
        if (this.byHandle.startsWith("@")) {
            this.byHandle = this.byHandle.substring(1);
        }

        for (int i = 0; i < 10; i++) {
            String pageHtml = WebRequest.sendHttpRequest(
                HttpRequest.newBuilder(URI.create(TiktokApi.TIKTOK_WEB_URL + "/@" + this.byHandle + "/live"))
                    .header("User-Agent", UA),
                BodyHandlers.ofString(),
                null
            ).body();

            if (pageHtml.contains("SlardarWAF")) {
//                System.out.println("SlardarWAF");
                try {
                    long jitter = ThreadLocalRandom.current().nextInt(0, 1000);
                    long timeout = (1000 * (i + 1)) + jitter; // 1 second, 2 seconds, etc. (with 0-1s of jitter)
                    Thread.sleep(timeout);
                } catch (InterruptedException ignored) {}
                continue;
            }

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

        throw new ApiException("We're getting firewalled!");
    }

}
