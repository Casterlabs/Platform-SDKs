package co.casterlabs.sdk.tiktok.unsupported.webcast.realtime;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.tiktok.unsupported.webcast.WebcastCookies;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse;
import co.casterlabs.sdk.tiktok.unsupported.webcast.requests.WebcastGetIMFetchEulerstreamRequest;

public class WebcastPushEulerstreamRealtime extends WebcastPushRealtime {

    @Override
    @SuppressWarnings("deprecation")
    protected void setup(String chatRoomId, WebcastCookies cookies) throws ApiAuthException, ApiException, IOException {
        WebcastResponse imData = new WebcastGetIMFetchEulerstreamRequest()
            .setRoomId(chatRoomId)
            .send();
        System.out.println(imData.getCursor());
        System.out.println(imData.getInternalExt());
        System.out.println(imData.getPushServer());
        System.out.println(imData.getRouteParamsMapMap());

        String uri = String.format(
            "%s?%s&cursor=%s&internal_ext=%s&live_id=12&roomid=%s&resp_content_type=protobuf&%s",
            imData.getPushServer(),
            cookies.getClientParams(),
            imData.getCursor(),
            URLEncoder.encode(imData.getInternalExt()),
            chatRoomId,
            imData
                .getRouteParamsMapMap()
                .entrySet()
                .stream()
                .map((entry) -> URLEncoder.encode(entry.getKey()) + '=' + URLEncoder.encode(entry.getValue()))
                .collect(Collectors.joining("&"))
        );
        System.out.println(uri);

        System.out.println(cookies.getCookie());

        this.conn = new Connection(uri, imData, cookies.getCookie());
    }

}
