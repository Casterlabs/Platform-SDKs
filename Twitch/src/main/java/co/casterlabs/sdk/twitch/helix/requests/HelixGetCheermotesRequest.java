package co.casterlabs.sdk.twitch.helix.requests;

import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.HttpUtil;
import co.casterlabs.sdk.twitch.TwitchApi;
import co.casterlabs.sdk.twitch.helix.TwitchHelixAuth;
import co.casterlabs.sdk.twitch.helix.types.HelixCheermote;
import lombok.NonNull;
import okhttp3.Response;

public class HelixGetCheermotesRequest extends AuthenticatedWebRequest<List<HelixCheermote>, TwitchHelixAuth> {
    private String id;

    public HelixGetCheermotesRequest(@NonNull TwitchHelixAuth auth) {
        super(auth);
    }

    public HelixGetCheermotesRequest setId(@Nullable String id) {
        this.id = id;
        return this;
    }

    @Override
    public List<HelixCheermote> execute() throws ApiException, ApiAuthException, IOException {
        String url = "https://api.twitch.tv/helix/bits/cheermotes";

        if (this.id != null) {
            url += "?broadcaster_id=" + this.id;
        }

        try (Response response = HttpUtil.sendHttpGet(url, null, auth)) {
            JsonObject json = TwitchApi.RSON.fromJson(response.body().string(), JsonObject.class);

            if (response.code() == 200) {
                return Rson.DEFAULT.fromJson(
                    json.get("data"),
                    new TypeToken<List<HelixCheermote>>() {
                    }
                );
            } else {
                throw new ApiException("Unable to get cheermotes: " + json.get("message").getAsString());
            }
        }
    }

}
