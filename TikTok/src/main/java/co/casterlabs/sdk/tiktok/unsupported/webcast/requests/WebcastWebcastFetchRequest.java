package co.casterlabs.sdk.tiktok.unsupported.webcast.requests;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

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
public class WebcastWebcastFetchRequest extends AuthenticatedWebRequest<WebcastResponse, WebcastCookies> {
    private String roomId;
    private @Nullable WebcastResponse cursor;

    public WebcastWebcastFetchRequest(@NonNull WebcastCookies auth) {
        super(auth);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected WebcastResponse execute() throws ApiException, ApiAuthException, IOException {
        Map<String, String> query = new HashMap<>();
        query.put("client", "Casterlabs TiktokApiJava");
        query.put("api_key", WebcastConstants.TIKTOK_SIGN_URL_API_KEY);
        query.put("room_id", this.roomId);
        query.put("uuc", "1");

        if (this.cursor != null) {
            query.put("cursor", this.cursor.getCursor());
            query.put("internal_ext", this.cursor.getInternalExt());
        }

        byte[] signedUrlResponse = this.auth.sendHttpRequestBytes(
            new Request.Builder()
                .url(
                    WebcastConstants.TIKTOK_SIGN_URL + "/webcast/fetch?" +
                        query
                            .entrySet()
                            .stream()
                            .map((entry) -> URLEncoder.encode(entry.getKey()) + '=' + URLEncoder.encode(entry.getValue()))
                            .collect(Collectors.joining("&"))
                )
        );

        return WebcastResponse.parseFrom(signedUrlResponse);
    }

}
