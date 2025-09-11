package co.casterlabs.sdk.tiktok.unsupported.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession;
import co.casterlabs.sdk.tiktok.unsupported.realtime.TiktokWebcastImRoomConnectionData;
import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.ProtoMessageFetchResult;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class EulerstreamGetImFetchRequest extends WebRequest<TiktokWebcastImRoomConnectionData> {
    private final TiktokWebSession session;
    private final @Nullable String apiKey;

    private String forRoomId;

    public EulerstreamGetImFetchRequest(@NonNull TiktokWebSession session, @Nullable String apiKey) {
        this.session = session;
        this.apiKey = apiKey;
    }

    @Override
    protected TiktokWebcastImRoomConnectionData execute() throws ApiException, ApiAuthException, IOException {
        QueryBuilder query = new QueryBuilder()
            .put("client", "github.com/Casterlabs/Platform-SDKs/tree/main/TikTok")
//            .put("user_agent", this.session.device().userAgent)
            .put("room_id", this.forRoomId)
            .optionalPut("apiKey", this.apiKey);

        if (this.session.sessionIdCookie() != null && this.session.targetIdcCookie() != null) {
            query
                .put("session_id", this.session.sessionIdCookie())
                .put("tt_target_idc", this.session.targetIdcCookie())
                .put("client_enter", true); // ?
        }

        String url = String.format("https://tiktok.eulerstream.com/webcast/fetch?%s", query);

        HttpResponse<byte[]> response = WebRequest.sendHttpRequest(
            this.session.createRequest(url),
            BodyHandlers.ofByteArray(),
            null
        );

        byte[] body = response.body();

        Optional<String> cookie = response.headers().firstValue("x-set-tt-cookie");
        if (cookie.isEmpty()) {
            throw new IllegalStateException("Sign server did not return any cookie headers! " + new String(body));
        }

        try {
            ProtoMessageFetchResult imFetch = ProtoMessageFetchResult.parseFrom(body);

            QueryBuilder wsQuery = this.session.wsQuery()
                .put("room_id", this.forRoomId)
                .put("cursor", imFetch.getCursor())
                .put("internal_ext", imFetch.getInternalExt());

            for (Map.Entry<String, String> entry : imFetch.getWsParamsMap().entrySet()) {
                if (entry.getValue().isEmpty()) continue;
                wsQuery.put(entry.getKey(), entry.getValue());
            }

            String wsUrl = String.format("%s?%s", imFetch.getWsUrl(), wsQuery);

            return new TiktokWebcastImRoomConnectionData(
                wsUrl,
                Long.parseLong(this.forRoomId),
                Map.of(
                    "Cookie", cookie.get(),
                    "User-Agent", this.session.device().userAgent
                ),
                imFetch
            );
        } catch (Throwable t) {
            throw new IOException(new String(body), t);
        }
    }

}
