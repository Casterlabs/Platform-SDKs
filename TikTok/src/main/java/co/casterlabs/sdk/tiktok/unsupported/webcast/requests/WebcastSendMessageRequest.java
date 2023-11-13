package co.casterlabs.sdk.tiktok.unsupported.webcast.requests;

import java.io.IOException;
import java.net.URLEncoder;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastConstants;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastCookies;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;
import okhttp3.RequestBody;

@Setter
@Accessors(chain = true)
public class WebcastSendMessageRequest extends AuthenticatedWebRequest<Void, WebcastCookies> {
    private String roomId;
    private String message;

    public WebcastSendMessageRequest(@NonNull WebcastCookies auth) {
        super(auth);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        String url = WebcastConstants.TIKTOK_WEBCAST_URL + "/webcast/room/chat/?" +
            this.auth.getClientParams() +
            "&room_id=" + this.roomId +
            "&channel=web_pc" +
            "&aid=1988" +
            "&device_platform=web_pc" +
            "&emotes_with_index" +
            "&content=" + URLEncoder.encode(this.message);

//        System.out.println(
        this.auth.sendHttpRequest(
            new Request.Builder()
                .url(url)
                .post(RequestBody.create("", null))
        );
//        );

        return null;
    }

}
