package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.URIParameters;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import lombok.NonNull;

public class TwitchHelixGetUsersChatColorRequest extends AuthenticatedWebRequest<List<String>, TwitchHelixAuth> {
    private List<String> ids = new LinkedList<>();

    public TwitchHelixGetUsersChatColorRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public TwitchHelixGetUsersChatColorRequest byId(@NonNull String id) {
        assert this.ids.size() < 100 : "You cannot request more than 100 ids.";
        this.ids.add(id);
        return this;
    }

    @Override
    protected List<String> execute() throws ApiException, ApiAuthException, IOException {
        if (this.ids.size() == 0) return Collections.emptyList(); // Don't even request.

        String url = "https://api.twitch.tv/helix/chat/color?" + new URIParameters()
            .putAllAsSeparateKeys("user_id", this.ids);

        JsonObject response = WebRequest.sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(url)),
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        ).body();

        if (response != null && !response.containsKey("data")) {
            throw new ApiException(response.toString());
        }

        return response
            .getArray("data")
            .toList()
            .stream()
            .map((e) -> e.getAsObject())
            .map((o) -> o.getString("color"))
            .toList();
    }

}
