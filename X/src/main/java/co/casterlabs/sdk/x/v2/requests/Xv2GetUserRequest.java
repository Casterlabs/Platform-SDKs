package co.casterlabs.sdk.x.v2.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.sdk.x.Xv2Auth;
import co.casterlabs.sdk.x.v2.types.Xv2User;
import lombok.NonNull;

public class Xv2GetUserRequest extends AuthenticatedWebRequest<Xv2User, Xv2Auth> {

    public Xv2GetUserRequest(@NonNull Xv2Auth auth) {
        super(auth);
    }

    @Override
    protected Xv2User execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.x.com/2/users/me?user.fields=created_at,description,id,name,profile_image_url,public_metrics,subscriber_count,url,username,verified,verified_type,subscription_type";

        return _ApiHelper.request(
            HttpRequest.newBuilder(URI.create(url)),
            this.auth,
            ResponseHolder.class
        ).data;
    }

    @JsonClass(exposeAll = true)
    private static class ResponseHolder {
        public final Xv2User data = null;

    }

}
