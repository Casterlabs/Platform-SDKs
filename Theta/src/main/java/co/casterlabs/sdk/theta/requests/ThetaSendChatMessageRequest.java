//package co.casterlabs.sdk.theta.requests;
//
//import java.io.IOException;
//
//import co.casterlabs.apiutil.auth.ApiAuthException;
//import co.casterlabs.apiutil.web.ApiException;
//import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
//import co.casterlabs.rakurai.json.element.JsonObject;
//import co.casterlabs.sdk.theta.ThetaAuth;
//import lombok.NonNull;
//
//public class ThetaSendChatMessageRequest extends AuthenticatedWebRequest<Void, ThetaAuth> {
//    private String toId;
//    private String message;
//
//    public ThetaSendChatMessageRequest(@NonNull ThetaAuth auth) {
//        super(auth);
//    }
//
//    public ThetaSendChatMessageRequest toId(String id) {
//        this.toId = id;
//        return this;
//    }
//
//    public ThetaSendChatMessageRequest withMessage(String message) {
//        this.message = message;
//        return this;
//    }
//
//    @Override
//    protected Void execute() throws ApiException, ApiAuthException, IOException {
//        String url = String.format("https://api.theta.tv/v1/channel/%s/channel_action", this.toId);
//
//        JsonObject body = new JsonObject()
//            .put("type", "chat_message")
//            .put("message", this.message);
//
//        System.out.println(ThetaRequester.httpPost(url, body, this.auth));
//
//        return null;
//    }
//
//}
