package co.casterlabs.dliveapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.dliveapijava.DliveApiJava;
import co.casterlabs.dliveapijava.DliveAuth;
import co.casterlabs.dliveapijava.types.DliveUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.NonNull;

public class DliveGetMyselfRequest extends AuthenticatedWebRequest<DliveUser, DliveAuth> {
    private static final String QUERY = "query{me{" + DliveUser.GQL_DATA + "}}";

    public DliveGetMyselfRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    @Override
    protected DliveUser execute() throws ApiException, ApiAuthException, IOException {
        JsonObject response = DliveHttpUtil.sendHttp(QUERY, this.auth);

        return DliveApiJava.RSON.fromJson(
            response
                .getObject("data")
                .get("me"),
            DliveUser.class
        );
    }

}
