package co.casterlabs.sdk.caffeine;

public class CaffeineEndpoints {
    // Prepend
    public static final String IMAGES = "https://images.caffeine.tv";
    public static final String ASSETS = "https://assets.caffeine.tv";

    // Formatted
    public static final String USERS = "https://api.caffeine.tv/v1/users/%s";
    public static final String FOLLOWERS = "https://api.caffeine.tv/v2/users/%s/followers?limit=%d&offset=%d";
    public static final String FOLLOWING = "https://api.caffeine.tv/v2/users/%s/following?limit=%d&offset=%d";
    public static final String SIGNED = "https://api.caffeine.tv/v1/users/%s/signed";
    public static final String CHAT_MESSAGE = "https://realtime.caffeine.tv/v2/reaper/stages/%s/messages";
    public static final String UPVOTE_MESSAGE = "https://realtime.caffeine.tv/v2/reaper/messages/%s/endorsements";
    public static final String BROADCASTS = "https://api.caffeine.tv/v1/broadcasts/%s";

    // Realtime
    public static final String CHAT = "wss://realtime.caffeine.tv/public/v2/reactions/stages/%s/messages";
    public static final String VIEWERS = "wss://realtime.caffeine.tv/v2/reaper/stages/%s/viewers";
    public static final String QUERY = "wss://realtime.caffeine.tv/public/graphql/query";

    // Standalone
    public static final String GAMES_LIST = "https://api.caffeine.tv/v1/games";
    public static final String PROPS_LIST = "https://payments.caffeine.tv/store/get-digital-items";
    public static final String SIGNIN = "https://api.caffeine.tv/v1/account/signin";
    public static final String TOKEN = "https://api.caffeine.tv/v1/account/token";

}
