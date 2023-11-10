package co.casterlabs.sdk.tiktok.unsupported.webcast.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastConstants;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastCookies;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Setter
@Accessors(chain = true)
public class WebcastGetIMFetchRequest extends AuthenticatedWebRequest<WebcastResponse, WebcastCookies> {
    private String roomId;
    private WebcastResponse cursor = null;

    public WebcastGetIMFetchRequest(@NonNull WebcastCookies auth) {
        super(auth);
    }

    @Override
    protected WebcastResponse execute() throws ApiException, ApiAuthException, IOException {
        String url = WebcastConstants.TIKTOK_WEBCAST_URL + "/webcast/im/fetch/?" +
            this.auth.getClientParams() +
            "&room_id=" + this.roomId;

        if (this.cursor != null) {
            url += "&cursor=" + this.cursor.getCursor();
            url += "&internal_ext=" + this.cursor.getInternalExt();
        }

        byte[] imResponse = this.auth.sendHttpRequestBytes(
            new Request.Builder()
                .url(url)
        );

        try {
            return WebcastResponse.parseFrom(imResponse);
        } catch (Throwable t) {
            throw new IOException(new String(imResponse), t);
        }
    }

}
