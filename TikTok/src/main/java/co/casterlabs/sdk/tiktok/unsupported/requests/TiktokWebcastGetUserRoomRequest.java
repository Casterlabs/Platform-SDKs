package co.casterlabs.sdk.tiktok.unsupported.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession;
import co.casterlabs.sdk.tiktok.unsupported.types.TiktokRoomData;
import co.casterlabs.sdk.tiktok.unsupported.types.TiktokWebcastRoomData;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TiktokWebcastGetUserRoomRequest extends WebRequest<TiktokRoomData> {
    private final TiktokWebSession session;
    private String byHandle;
    private boolean failIfOffline;

    public TiktokWebcastGetUserRoomRequest(@NonNull TiktokWebSession session) {
        this.session = session;
    }

    @Override
    protected TiktokRoomData execute() throws ApiException, ApiAuthException, IOException {
        if (this.byHandle.startsWith("@")) {
            this.byHandle = this.byHandle.substring(1);
        }

        QueryBuilder query = this.session.httpQuery()
            .put("unique_id", this.byHandle);
        String url = String.format("%s/room/info_by_user/?%s", this.session.webcastUrl(), query);

        JsonObject response = WebRequest.sendHttpRequest(
            this.session.createRequest(url),
            RsonBodyHandler.of(JsonObject.class),
            null
        ).body();

        JsonObject data = response.getObject("data");

        if (data.containsKey("prompts") && data.getString("prompts").contains("LIVE has ended")) {
            // Never went live or it was properly ended.
            if (this.failIfOffline) {
                throw new IllegalStateException("User is not live.");
            } else {
                return TiktokRoomData.OFFLINE;
            }
        }

        return new TiktokWebcastRoomData(data);
    }

}
