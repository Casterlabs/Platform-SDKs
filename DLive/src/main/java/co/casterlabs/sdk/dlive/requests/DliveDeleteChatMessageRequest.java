package co.casterlabs.sdk.dlive.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class DliveDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, DliveAuth> {
    private static final String QUERY = "mutation{chatDelete(streamer:%s,id:%s){err{code}}";

    private String streamer;
    private String id;

    public DliveDeleteChatMessageRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.streamer != null : "Must setStreamer()";
        assert this.id != null : "Must setId()";

        DliveHttpUtil.sendHttp(
            String.format(
                QUERY,
                new JsonString(this.streamer), new JsonString(this.id)
            ),
            this.auth
        );

        return null;
    }

}
