package co.casterlabs.sdk.dlive.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.sdk.dlive.DliveHttpUtil;
import co.casterlabs.sdk.dlive.types.DliveUser;
import lombok.NonNull;

public class DliveGetMyselfRequest extends AuthenticatedWebRequest<DliveUser, DliveAuth> {
    private static final String QUERY = "query{me{" + DliveUser.GQL_DATA + "}}";

    public DliveGetMyselfRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected DliveUser execute() throws ApiException, ApiAuthException, IOException {
        JsonObject response = DliveHttpUtil.sendHttp(QUERY, this.auth);

        return Rson.DEFAULT.fromJson(
            response
                .getObject("data")
                .get("me"),
            DliveUser.class
        );
    }

}
