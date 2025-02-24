package co.casterlabs.sdk.trovo.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.trovo.TrovoAuth;
import co.casterlabs.sdk.trovo.requests.TrovoSendChatCommandRequest.SendChatCommandResult;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TrovoSendChatCommandRequest extends AuthenticatedWebRequest<SendChatCommandResult, TrovoAuth> {
    public static final String URL = "https://open-api.trovo.live/openplatform/channels/command";

    private String withCommand;
    private String forChannelId;

    public TrovoSendChatCommandRequest(@NonNull TrovoAuth auth) {
        super(auth);
    }

    public TrovoSendChatCommandRequest setCommand(@NonNull String command) {
        if (command.startsWith("/")) {
            command = command.substring(1);
        }

        this.withCommand = command;

        return this;
    }

    @Override
    protected SendChatCommandResult execute() throws ApiException, ApiAuthException, IOException {
        assert this.forChannelId != null : "You must set a channel id.";
        assert this.withCommand != null : "You must set a command.";
        assert !this.auth.isApplicationAuth() : "You must use user auth for this request.";

        JsonObject body = new JsonObject()
            .put("command", this.withCommand)
            .put("channel_id", this.forChannelId);

        return WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json"),
            RsonBodyHandler.of(SendChatCommandResult.class),
            this.auth
        ).body();
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class SendChatCommandResult {
        @JsonField("is_success")
        private boolean isSuccess;

        @JsonField("display_msg")
        private String errorMessage;

    }

}
