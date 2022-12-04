package co.casterlabs.dliveapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.dliveapijava.DliveAuth;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class DliveSendChatMessageRequest extends AuthenticatedWebRequest<Void, DliveAuth> {
    private static final String QUERY = "mutation{sendStreamchatMessage(input:{streamer:%s,message:%s,roomRole:Member,subscribing:false}){message{type}}}";

    private String streamer;
    private String message;

    public DliveSendChatMessageRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.streamer != null : "Must setStreamer()";
        assert this.message != null : "Must setMessage()";

        DliveHttpUtil.sendHttp(
            String.format(
                QUERY,
                new JsonString(this.streamer), new JsonString(this.message)
            ),
            this.auth
        );

        return null;
    }

}
