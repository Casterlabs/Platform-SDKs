package co.casterlabs.sdk.dlive.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import co.casterlabs.sdk.dlive.DliveAuth;
import co.casterlabs.sdk.dlive.DliveHttpUtil;
import co.casterlabs.sdk.dlive.types.DliveUser;
import lombok.NonNull;

public class DliveGetUserRequest extends AuthenticatedWebRequest<DliveUser, DliveAuth> {
    private static final String QUERY = "query{%s(%s){" + DliveUser.GQL_DATA + "}}";

    private String queryType;
    private String queryVariables;

    public DliveGetUserRequest(@NonNull DliveAuth auth) {
        super(auth);
    }

    public DliveGetUserRequest byUsername(@NonNull String username) {
        this.queryType = "user";
        this.queryVariables = String.format("username:%s", new JsonString(username));
        return this;
    }

    public DliveGetUserRequest byDisplayname(@NonNull String display) {
        this.queryType = "userByDisplayName";
        this.queryVariables = String.format("displayname:%s", new JsonString(display));
        return this;
    }

    @Override
    protected DliveUser execute() throws ApiException, ApiAuthException, IOException {
        assert this.queryType != null : "Must byUsername() or byDisplayname()";

        JsonObject response = DliveHttpUtil.sendHttp(String.format(QUERY, this.queryType, this.queryVariables), this.auth);

        return Rson.DEFAULT.fromJson(
            response
                .getObject("data")
                .get(this.queryType),
            DliveUser.class
        );
    }

}
