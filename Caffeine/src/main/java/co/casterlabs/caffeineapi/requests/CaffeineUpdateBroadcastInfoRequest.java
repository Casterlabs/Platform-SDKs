package co.casterlabs.caffeineapi.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.caffeineapi.CaffeineAuth;
import co.casterlabs.caffeineapi.CaffeineEndpoints;
import co.casterlabs.caffeineapi.HttpUtil;
import co.casterlabs.caffeineapi.types.BroadcastRating;
import co.casterlabs.caffeineapi.types.CaffeineBroadcast;
import co.casterlabs.caffeineapi.types.CaffeineGame;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class CaffeineUpdateBroadcastInfoRequest extends AuthenticatedWebRequest<Void, CaffeineAuth> {
    private @NonNull String broadcastId;
    private @NonNull String title;
    private @NonNull BroadcastRating rating;
    private long gameId = 79;

    private @Nullable byte[] thumbnailBytes;

    public CaffeineUpdateBroadcastInfoRequest(CaffeineAuth auth) {
        super(auth);
    }

    public CaffeineUpdateBroadcastInfoRequest setBroadcast(@NonNull CaffeineBroadcast broadcast) {
        this.broadcastId = broadcast.getId();
        return this;
    }

    public CaffeineUpdateBroadcastInfoRequest setGame(@NonNull CaffeineGame game) {
        this.gameId = game.getId();
        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.broadcastId != null : "Broadcast ID must be set";
        assert this.title != null : "Title must be set";
        assert this.rating != null : "Rating must be set";

//        if ((this.rating == BroadcastRating.MATURE) && !this.title.startsWith("[17+] ")) {
//            this.title = "[17+] " + this.title;
//        }

        MultipartBody.Builder form = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("broadcast[name]", this.title)
            .addFormDataPart("broadcast[content_rating]", this.rating.getApi())
            .addFormDataPart("broadcast[game_id]", String.valueOf(this.gameId));

        if (this.thumbnailBytes != null) {
            final String fileType = "gif"; // Caffeine doesn't actually validate this, LMFAO

            form.addFormDataPart(
                "broadcast[game_image]",
                "image." + fileType,
                RequestBody.create(
                    this.thumbnailBytes,
                    MediaType.parse("image/" + fileType)
                )
            );
        }

        try (Response response = HttpUtil.sendHttp(
            form.build(),
            "PATCH",
            String.format(CaffeineEndpoints.BROADCASTS, this.broadcastId),
            this.auth,
            "application/x-www-form-urlencoded"
        )) {}

        return null;
    }

}
