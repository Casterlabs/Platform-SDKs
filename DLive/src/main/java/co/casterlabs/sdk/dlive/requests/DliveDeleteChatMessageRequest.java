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
public class DliveDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, DliveAuth> {
    private static final String QUERY = "mutation{chatDelete(streamer:%s,id:%s){err{code}}";

    private String forStreamer;
    private String forId;

    public DliveDeleteChatMessageRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        assert this.forStreamer != null : "Must specify a streamer";
        assert this.forId != null : "Must specify a message id";

        DliveHttpUtil.sendHttp(
            String.format(
                QUERY,
                new JsonString(this.forStreamer), new JsonString(this.forId)
            ),
            this.auth
        );

        return null;
    }

}
