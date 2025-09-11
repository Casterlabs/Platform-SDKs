package co.casterlabs.sdk.tiktok.unsupported.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession;
import co.casterlabs.sdk.tiktok.unsupported.types.TiktokRoomData;
import co.casterlabs.sdk.tiktok.unsupported.types.TiktokWebRoomData;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TiktokWebGetLiveUserRoomRequest extends WebRequest<TiktokRoomData> {
    private final TiktokWebSession session;
    private String byHandle;

    public TiktokWebGetLiveUserRoomRequest(@NonNull TiktokWebSession session) {
        this.session = session;
    }

    @Override
    protected TiktokRoomData execute() throws ApiException, ApiAuthException, IOException {
        QueryBuilder query = this.session.baseQuery()
            .put("uniqueId", this.byHandle)
            .put("sourceType", 54);
        String url = String.format("%s/api-live/user/room/?%s", this.session.webUrl(), query);

        JsonObject response = WebRequest.sendHttpRequest(
            this.session.createRequest(url),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        JsonElement data = response.get("data");
        if (data.isJsonNull()) {
            throw new ApiException("No room data available (user has never streamed).");
        }

        return Rson.DEFAULT.fromJson(data, TiktokWebRoomData.class);
    }

}
