package co.casterlabs.sdk.glimesh.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.sdk.glimesh.GlimeshAuth;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class GlimeshSendChatMessageRequest extends AuthenticatedWebRequest<Void, GlimeshAuth> {
    private static final String MUTATION = "mutation{createChatMessage(channelId:%s,message:{message:%s}){insertedAt}}";

    private String message;
    private String channelId;

    public GlimeshSendChatMessageRequest(@NonNull GlimeshAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        String query = String.format(MUTATION, this.channelId, new JsonString(this.message).toString());

        try (Response response = GlimeshHttpUtil.sendHttp(query, this.auth)) {
            String body = response.body().string();

            JsonObject json = Rson.DEFAULT.fromJson(body, JsonObject.class);

            if (response.code() == 401) {
                throw new ApiAuthException(json.toString());
            } else if (json.containsKey("errors")) {
                throw new ApiException(json.toString());
            } else {
                return null;
            }
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
    }

}
