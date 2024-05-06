package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import co.casterlabs.sdk.youtube.YoutubeHttpUtil;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class YoutubeInsertLiveChatMessageRequest extends AuthenticatedWebRequest<Void, YoutubeAuth> {
    private @Setter @NonNull String liveChatId = null;
    private @Setter @Nullable String messageText = null;

    public YoutubeInsertLiveChatMessageRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.liveChatId != null : "You must specify a chat id.";
        assert this.messageText != null : "You must set `messageText`.";

        final String url = "https://youtube.googleapis.com/youtube/v3/liveChat/messages?part=snippet";

        if (this.auth.isApplicationAuth()) {
            throw new ApiAuthException("You must use user auth when sending a chat message.");
        }

        JsonObject body = new JsonObject()
            .put(
                "snippet",
                new JsonObject()
                    .put("liveChatId", this.liveChatId)
                    .put("type", "textMessageEvent")
                    .put(
                        "textMessageDetails",
                        new JsonObject()
                            .put("messageText", this.messageText)
                    )
            );

        YoutubeHttpUtil.insert(body.toString(), "application/json", url, this.auth);
        return null;
    }

}
