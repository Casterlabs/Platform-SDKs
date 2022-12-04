package co.casterlabs.sdk.youtube.requests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.sdk.youtube.HttpUtil;
import co.casterlabs.sdk.youtube.YoutubeAuth;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        RequestBody request = RequestBody.create(
            new JsonObject()
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
                )
                .toString()
                .getBytes(StandardCharsets.UTF_8)
        );

        try (Response response = HttpUtil.sendHttp(request, "POST", url, this.auth, "application/json")) {
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
