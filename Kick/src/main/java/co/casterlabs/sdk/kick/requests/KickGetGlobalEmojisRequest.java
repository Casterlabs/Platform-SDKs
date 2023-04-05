package co.casterlabs.sdk.kick.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.sdk.kick.KickApi;
import co.casterlabs.sdk.kick.types.KickEmoji;
import lombok.experimental.Accessors;
import okhttp3.Request;

@Accessors(chain = true)
public class KickGetGlobalEmojisRequest extends WebRequest<List<KickEmoji>> {
    private static final Pattern APP_SCRIPT_REGEX = Pattern.compile("https:\\/\\/[a-zA-Z0-9]+.cloudfront.net\\/[a-zA-Z0-9-]+\\/build\\/assets\\/app.[a-zA-Z0-9]+\\.js");
    private static final Pattern CHATROOM_SCRIPT_REGEX = Pattern.compile("assets\\/Chatroom.[a-zA-Z0-9]+\\.js");
    private static final Pattern EMOJIS_REGEX = Pattern.compile("\\[[A-Za-z0-9\"-, ]*\"angry\"[A-Za-z0-9\"-, ]*\\]");

    @Override
    protected List<KickEmoji> execute() throws ApiException, ApiAuthException, IOException {
        String appScriptUrl = getAppScriptUrl(); // "https://dbxmjjzl5pc1g.cloudfront.net/35dc3647-91ef-4580-b1d5-0083a561d8f8/build/assets/app.64632742.js"
        String basePath = appScriptUrl.substring(0, appScriptUrl.indexOf("/build/assets/app.")); // "https://dbxmjjzl5pc1g.cloudfront.net/35dc3647-91ef-4580-b1d5-0083a561d8f8"
        String chatRoomScriptPath = getChatroomScriptUrl(appScriptUrl); // "https://dbxmjjzl5pc1g.cloudfront.net/35dc3647-91ef-4580-b1d5-0083a561d8f8/build/assets/Chatroom.c16d7373.js"
        String emojiList = getChatroomEmojis(basePath + "/build/" + chatRoomScriptPath); // "[\"angry\", ..."

        String[] emojiNames = Rson.DEFAULT.fromJson(emojiList, String[].class); // It'll be a javascript array, which we can treat as regular json.

        List<KickEmoji> emojis = new ArrayList<>(emojiNames.length);
        for (String emojiName : emojiNames) {
            emojis.add(
                new KickEmoji(
                    emojiName,
                    basePath + "/images/emojis/" + emojiName + ".png"
                )
            );
        }

        return emojis;
    }

    private static String getAppScriptUrl() throws IOException {
        String homepage = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(KickApi.API_BASE_URL),
            null
        );

        Matcher matcher = APP_SCRIPT_REGEX.matcher(homepage);
        if (!matcher.find()) throw new IllegalStateException("Could not parse Kick homepage.");

        String url = matcher.group();
        return url;
    }

    private static String getChatroomScriptUrl(String appScriptUrl) throws IOException {
        String appScript = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(appScriptUrl),
            null
        );

        Matcher matcher = CHATROOM_SCRIPT_REGEX.matcher(appScript);
        if (!matcher.find()) throw new IllegalStateException("Could not parse Kick app script.");

        String url = matcher.group();
        return url;
    }

    private static String getChatroomEmojis(String chatRoomScriptUrl) throws IOException {
        String chatRoomScript = WebRequest.sendHttpRequest(
            new Request.Builder()
                .url(chatRoomScriptUrl),
            null
        );

        Matcher matcher = EMOJIS_REGEX.matcher(chatRoomScript);
        if (!matcher.find()) throw new IllegalStateException("Could not parse Kick chatroom script.");

        String url = matcher.group();
        return url;
    }

}
