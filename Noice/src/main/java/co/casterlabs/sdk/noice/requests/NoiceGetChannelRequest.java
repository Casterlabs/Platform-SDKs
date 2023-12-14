package co.casterlabs.sdk.noice.requests;

import java.io.IOException;
import java.util.Map;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.noice.NoiceAuth;
import co.casterlabs.sdk.noice.types.NoiceChannel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class NoiceGetChannelRequest extends AuthenticatedWebRequest<NoiceChannel, NoiceAuth> {
    private static final String QUERY = "query CurrentChannel($channelName: String!) {\n  channels(name: $channelName) {\n    channels {\n      ...CurrentChannel\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment CurrentChannel on ChannelChannel {\n  id\n  name\n  title\n  liveStatus\n  streamer {\n    userId\n    __typename\n  }\n  userBanStatus {\n    banned\n    __typename\n  }\n  ...ChannelButtons\n  ...ChannelHomeChannel\n  ...ChannelPageHeader\n  ...ChannelPageTop\n  __typename\n}\n\nfragment ChannelButtons on ChannelChannel {\n  liveStatus\n  ...ChannelActionButtonsChannel\n  __typename\n}\n\nfragment ChannelActionButtonsChannel on ChannelChannel {\n  id\n  streamerId\n  name\n  following\n  ...SubscriptionButtonChannel\n  __typename\n}\n\nfragment SubscriptionButtonChannel on ChannelChannel {\n  following\n  subscriptionConfig {\n    channelId\n    subscriptionsEnabled\n    __typename\n  }\n  subscription {\n    id\n    state\n    __typename\n  }\n  ...SubscriptionModalChannel\n  ...ActiveSubscriptionMenuChannel\n  __typename\n}\n\nfragment SubscriptionModalChannel on ChannelChannel {\n  id\n  subscription {\n    id\n    state\n    __typename\n  }\n  ...SubscriptionDialogChannelContentChannel\n  ...SubscriptionModalOverviewStageChannel\n  ...SubscriptionModalCompleteStageChannel\n  ...SubscriptionModalActionsChannel\n  __typename\n}\n\nfragment SubscriptionDialogChannelContentChannel on ChannelChannel {\n  name\n  logo\n  followerCount\n  ...ChannelLogoChannel\n  __typename\n}\n\nfragment ChannelLogoChannel on ChannelChannel {\n  liveStatus\n  logo\n  name\n  __typename\n}\n\nfragment SubscriptionModalOverviewStageChannel on ChannelChannel {\n  name\n  ...SubscriptionGetChannelEmojisChannel\n  __typename\n}\n\nfragment SubscriptionGetChannelEmojisChannel on ChannelChannel {\n  subscriptionConfig {\n    channelId\n    tiers {\n      level\n      entitlements {\n        itemId\n        item {\n          id\n          type\n          children {\n            id\n            type\n            details {\n              ... on EmojiEmoji {\n                ...SubscriptionGetChannelEmojisChannelEmoji\n                __typename\n              }\n              __typename\n            }\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment SubscriptionGetChannelEmojisChannelEmoji on EmojiEmoji {\n  id\n  image\n  name\n  __typename\n}\n\nfragment SubscriptionModalCompleteStageChannel on ChannelChannel {\n  name\n  __typename\n}\n\nfragment SubscriptionModalActionsChannel on ChannelChannel {\n  subscription {\n    id\n    state\n    __typename\n  }\n  subscriptionConfig {\n    channelId\n    tiers {\n      level\n      prices {\n        period\n        price\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment ActiveSubscriptionMenuChannel on ChannelChannel {\n  id\n  ...SubscriptionDialogChannelContentChannel\n  ...SubscriptionGetChannelEmojisChannel\n  subscription {\n    id\n    expiresAt\n    __typename\n  }\n  __typename\n}\n\nfragment ChannelHomeChannel on ChannelChannel {\n  name\n  offlineBanner\n  ...ChannelDescription\n  ...ChannelPageLinks\n  __typename\n}\n\nfragment ChannelDescription on ChannelChannel {\n  description\n  __typename\n}\n\nfragment ChannelPageLinks on ChannelChannel {\n  links {\n    type\n    name\n    url\n    __typename\n  }\n  __typename\n}\n\nfragment ChannelPageHeader on ChannelChannel {\n  id\n  name\n  offlineBanner\n  liveStatus\n  currentStreamId\n  ...LiveChannelHeaderChannel\n  __typename\n}\n\nfragment LiveChannelHeaderChannel on ChannelChannel {\n  id\n  currentStreamId\n  thumbnailUrl\n  ...ChannelFriendsListChannel\n  ...LiveChannelHeaderActionsChannel\n  ...LiveChannelHeaderStreamInfoChannel\n  __typename\n}\n\nfragment ChannelFriendsListChannel on ChannelChannel {\n  id\n  channelFriends {\n    totalCount\n    __typename\n  }\n  __typename\n}\n\nfragment LiveChannelHeaderActionsChannel on ChannelChannel {\n  id\n  name\n  game {\n    id\n    activeSeason {\n      id\n      seasonBreak\n      seasonBreakReason\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment LiveChannelHeaderStreamInfoChannel on ChannelChannel {\n  title\n  viewerCount\n  game {\n    id\n    name\n    __typename\n  }\n  __typename\n}\n\nfragment ChannelPageTop on ChannelChannel {\n  name\n  followerCount\n  ...ChannelLogoChannel\n  __typename\n}";

    private @Setter String channelName;

    public NoiceGetChannelRequest(@NonNull NoiceAuth auth) {
        super(auth);
    }

    @Override
    protected NoiceChannel execute() throws ApiException, ApiAuthException, IOException {
        assert this.channelName != null : "You must specify a channel name to query for.";

        JsonObject response = NoiceHttpUtil.sendHttp(this.auth, QUERY, Map.of("channelName", this.channelName));

        System.out.println(response);

        return Rson.DEFAULT.fromJson(
            response
                .getObject("data")
                .getObject("channels")
                .getArray("channels")
                .get(0),
            NoiceChannel.class
        );
    }

}
