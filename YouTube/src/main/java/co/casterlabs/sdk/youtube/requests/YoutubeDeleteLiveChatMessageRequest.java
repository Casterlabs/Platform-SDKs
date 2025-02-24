package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;
import org.unbescape.uri.UriEscape;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.YoutubeHttpUtil;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class YoutubeDeleteLiveChatMessageRequest extends AuthenticatedWebRequest<Void, YoutubeAuth> {
    private @Nullable String byMessageId = null;

    public YoutubeDeleteLiveChatMessageRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.byMessageId != null : "You must specify a message id.";

        String url = "https://youtube.googleapis.com/youtube/v3/liveChat/messages"
            + "?id=" + UriEscape.escapeUriQueryParam(this.byMessageId);

        if (this.auth.isApplicationAuth()) {
            throw new ApiAuthException("You must use user auth when sending a chat message.");
        }

        YoutubeHttpUtil.delete(url, this.auth);
        return null;
    }

}
