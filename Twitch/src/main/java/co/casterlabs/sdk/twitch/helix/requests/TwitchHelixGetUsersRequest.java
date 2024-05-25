package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.NonNull;

/**
 * Don't include any ids/logins and user a User Auth to get the current
 * authenticated user's profile information.
 * 
 * <b>email</b> will be unless you request with a user access token with the
 * <b>user:read:email</b> scope.
 */
public class TwitchHelixGetUsersRequest extends AuthenticatedWebRequest<List<HelixUser>, TwitchHelixAuth> {
    private List<String> ids = new LinkedList<>();
    private List<String> logins = new LinkedList<>();

    public TwitchHelixGetUsersRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public TwitchHelixGetUsersRequest byId(@NonNull String id) {
        assert this.ids.size() < 50 : "You cannot request more than 50 ids.";
        this.ids.add(id);
        return this;
    }

    public TwitchHelixGetUsersRequest byLogin(@NonNull String login) {
        assert this.logins.size() < 50 : "You cannot request more than 50 logins.";
        this.logins.add(login);
        return this;
    }

    @Override
    protected List<HelixUser> execute() throws ApiException, ApiAuthException, IOException {
        if (this.ids.size() + this.logins.size() == 0) {
            if (this.auth.isApplicationAuth()) {
                assert false : "You supplied an App Auth without any ids/logins to query for.";
            }
            // Let it through (get the authenticated user's profile).
        }

        String url = "https://api.twitch.tv/helix/users?" + new URIParameters()
            .putAllAsSeparateKeys("id", this.ids)
            .putAllAsSeparateKeys("login", this.logins);

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url)),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return Rson.DEFAULT.fromJson(response.get("data"), new TypeToken<List<HelixUser>>() {
        });
    }

}
