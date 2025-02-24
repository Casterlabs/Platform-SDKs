package co.casterlabs.sdk.dlive.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.sdk.dlive.DliveHttpUtil;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class DliveSendChatMessageRequest extends AuthenticatedWebRequest<Void, DliveAuth> {
    private static final String QUERY = "mutation{sendStreamchatMessage(input:{streamer:%s,message:%s,roomRole:Member,subscribing:false}){message{type}}}";

    private String forStreamer;
    private String withMessage;

    public DliveSendChatMessageRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forStreamer != null : "Must specify a streamer";
        assert this.withMessage != null : "Must specify a message";

        DliveHttpUtil.sendHttp(
            String.format(
                QUERY,
                new JsonString(this.forStreamer), new JsonString(this.withMessage)
            ),
            this.auth
        );

        return null;
    }

}
