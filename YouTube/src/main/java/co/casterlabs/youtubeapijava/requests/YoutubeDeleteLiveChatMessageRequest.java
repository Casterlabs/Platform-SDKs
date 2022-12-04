package co.casterlabs.youtubeapijava.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.youtubeapijava.HttpUtil;
import co.casterlabs.youtubeapijava.YoutubeAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class YoutubeDeleteLiveChatMessageRequest extends AuthenticatedWebRequest<Void, YoutubeAuth> {
    private @Setter @Nullable String messageId = null;

    public YoutubeDeleteLiveChatMessageRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.messageId != null : "You must specify a message id.";

        String url = "https://youtube.googleapis.com/youtube/v3/liveChat/messages"
            + "?id=" + HttpUtil.encodeURIComponent(this.messageId);

        if (this.auth.isApplicationAuth()) {
            throw new ApiAuthException("You must use user auth when sending a chat message.");
        }

        try (Response response = HttpUtil.sendHttp(null, "DELETE", url, this.auth, "application/json")) {
            String body = response.body().string();

            if (response.isSuccessful()) {
                return null;
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
