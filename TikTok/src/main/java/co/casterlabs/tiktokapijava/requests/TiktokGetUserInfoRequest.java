package co.casterlabs.tiktokapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.tiktokapijava.TiktokAuth;
import co.casterlabs.tiktokapijava.types.TiktokUserInfo;
import lombok.NonNull;
import okhttp3.Request;

public class TiktokGetUserInfoRequest extends AuthenticatedWebRequest<TiktokUserInfo, TiktokAuth> {

    public TiktokGetUserInfoRequest(@NonNull TiktokAuth auth) {
        super(auth);
    }

    @Override
    protected TiktokUserInfo execute() throws ApiException, ApiAuthException, IOException {
        final String url = "https://open.tiktokapis.com/v2/user/info/?fields=open_id,union_id,avatar_url,display_name,bio_description,profile_deep_link,is_verified,follower_count,following_count,likes_count";

        String response = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(url),
            this.auth
        );

        JsonObject json = Rson.DEFAULT.fromJson(response, JsonObject.class);
        JsonObject data = json.getObject("data").getObject("user");

        return Rson.DEFAULT.fromJson(data, TiktokUserInfo.class);
    }

}
