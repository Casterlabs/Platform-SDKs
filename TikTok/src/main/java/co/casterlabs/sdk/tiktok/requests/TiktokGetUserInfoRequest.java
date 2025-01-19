package co.casterlabs.sdk.tiktok.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.tiktok.TiktokApi;
import co.casterlabs.sdk.tiktok.TiktokAuth;
import co.casterlabs.sdk.tiktok.types.TiktokUserInfo;
import lombok.NonNull;

public class TiktokGetUserInfoRequest extends AuthenticatedWebRequest<TiktokUserInfo, TiktokAuth> {

    public TiktokGetUserInfoRequest(@NonNull TiktokAuth auth) {
        super(auth);
    }

    @Override
    protected TiktokUserInfo execute() throws ApiException, ApiAuthException, IOException {
        final String url = TiktokApi.TIKTOK_OPENAPI_URL + "/v2/user/info/?fields=open_id,union_id,avatar_url,display_name,bio_description,profile_deep_link,is_verified,follower_count,following_count,likes_count";

        JsonObject json = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder(URI.create(url)),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        JsonObject data = json.getObject("data").getObject("user");
        return Rson.DEFAULT.fromJson(data, TiktokUserInfo.class);
    }

}
