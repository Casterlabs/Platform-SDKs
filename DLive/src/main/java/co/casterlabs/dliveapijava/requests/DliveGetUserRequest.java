package co.casterlabs.dliveapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.dliveapijava.DliveApiJava;
import co.casterlabs.dliveapijava.DliveAuth;
import co.casterlabs.dliveapijava.types.DliveUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;

public class DliveGetUserRequest extends AuthenticatedWebRequest<DliveUser, DliveAuth> {
    private static final String QUERY = "query{%s(%s){" + DliveUser.GQL_DATA + "}}";

    private String queryType;
    private String queryVariables;

    public DliveGetUserRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    public DliveGetUserRequest queryByUsername(@NonNull String username) {
        this.queryType = "user";
        this.queryVariables = String.format("username:%s", new JsonString(username));
        return this;
    }

    public DliveGetUserRequest queryByDisplayname(@NonNull String display) {
        this.queryType = "userByDisplayName";
        this.queryVariables = String.format("displayname:%s", new JsonString(display));
        return this;
    }

    @Override
    protected DliveUser execute() throws ApiException, ApiAuthException, IOException {
        assert this.queryType != null : "Must queryByUsername() or queryByDisplayname()";

        JsonObject response = DliveHttpUtil.sendHttp(String.format(QUERY, this.queryType, this.queryVariables), this.auth);

        return DliveApiJava.RSON.fromJson(
            response
                .getObject("data")
                .get(this.queryType),
            DliveUser.class
        );
    }

}
