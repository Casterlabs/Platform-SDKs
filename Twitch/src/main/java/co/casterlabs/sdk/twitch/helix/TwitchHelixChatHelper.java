package co.casterlabs.sdk.twitch.helix;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixCreateStreamMarkerRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixDeleteChatMessageRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixGetChannelModeratorsRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixGetChannelVIPsRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixGetUsersRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSendAnnouncementRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSendChatMessageRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSendShoutoutRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSetBanStatusRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSetModeratorStatusRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSetRaidStatusRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixSetVIPStatusRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixStartCommercialRequest;
import co.casterlabs.sdk.twitch.helix.requests.TwitchHelixUpdateChatSettingsRequest;
import co.casterlabs.sdk.twitch.helix.types.HelixSimpleUser;
import co.casterlabs.sdk.twitch.helix.types.HelixUser;
import lombok.NonNull;

public class TwitchHelixChatHelper {

    /**
     * This method attempts to parse out the common chat commands and will send the
     * appropriate requests.
     * 
     * @return A message reply, if a command was executed. Otherwise null.
     */
    public static @Nullable TwitchHelixChatResult sendCommandOrMessage(@NonNull String forBroadcasterId, @NonNull String asSenderId, @NonNull String message, @Nullable String replyTo, @NonNull TwitchHelixAuth auth) {
        try {
            if (!message.startsWith("/") && !message.startsWith(".")) {
                // Not a command. Ignore it.
                new TwitchHelixSendChatMessageRequest(auth)
                    .forBroadcasterId(forBroadcasterId)
                    .asSenderId(asSenderId)
                    .message(message)
                    .replyTo(replyTo)
                    .send();
                return null;
            }

            String[] args = message.split(" +");

            // Try to figure out the comamnd used (remove the leading / or .)
            // Documentation: https://help.twitch.tv/s/article/chat-commands?language=en_US
            switch (args[0].substring(1)) {
                case "help": {
                    return new TwitchHelixChatResult(
                        "help",
                        String.join(
                            "\n",
                            "/mods                  - Lists all moderators.",
                            "/mod {user}            - Makes the specified user a moderator.",
                            "/unmod {user}          - Makes the specified user no longer a moderator.",
                            "/vips                  - Lists all VIPs.",
                            "/vip {user}            - Makes the specified user a VIP.",
                            "/unvip {user}          - Makes the specified user no longer a VIP.",
                            "/pin {text}            - Pins the specified message in chat.",
                            "/shoutout {user}       - Shouts out the specified user's channel.",
                            "/announce {text}       - Creates a highlighted message in chat.",
                            "/timeout {user} {time} - Times a user out for the specified amount of seconds.",
                            "/ban {user}            - Bans a user from the chatroom, permanently.",
                            "/unban {user}          - Unbans a user from the chatroom.",
                            "/clear                 - Clears the chat room.",
                            "/slow                  - Enables slow mode.",
                            "/slowoff               - Disables slow mode.",
                            "/followers [duration]  - Enables followers-only mode based on how long they've followed (default: 0 minutes).",
                            "/followersoff          - Disables followers-only mode.",
                            "/subscribers           - Enables subscribers-only mode. Only subscribers, the broadcaster, and moderators will be able to send messages.",
                            "/subscribersoff        - Disables subscribers-only mode.",
                            "/uniquechat            - Requires all messages be unique.",
                            "/uniquechatoff         - Disables unique message requirements.",
                            "/emoteonly             - Enables emote-only mode.",
                            "/emoteonlyoff          - Disables emote-only mode.",
                            "/commercial [30-180]   - Immediately runs a commercial for the seconds specified (default: 30 seconds).",
                            "/raid {user}           - Prepares the channel for a raid.",
                            "/unraid                - Cancels a pending raid.",
                            "/marker [text]         - Adds a marker in your VOD at this current timestamp, useful for editing."
                        ),
                        Map.of()
                    );
                }

                /* ------------------------ */
                /* Moderators               */
                /* ------------------------ */
                case "mods": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /mods", Map.of());
                    }

                    List<HelixSimpleUser> moderators = new TwitchHelixGetChannelModeratorsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .send();

                    if (moderators.isEmpty()) {
                        return new TwitchHelixChatResult("mods.none", "There are no moderators", Map.of());
                    } else {
                        String list = moderators
                            .stream()
                            .map((u) -> u.displayName)
                            .collect(Collectors.joining(", "));
                        return new TwitchHelixChatResult(
                            "mods.list",
                            "Channel moderators: " + list,
                            Map.of("list", list)
                        );
                    }
                }

                case "mod": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /mod {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetModeratorStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .targetUserId(user.id)
                        .shouldBeModerator(true)
                        .send();

                    return new TwitchHelixChatResult("mod.success", user.displayName + " is now a moderator", Map.of("user", user.displayName));
                }

                case "unmod": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /unmod {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetModeratorStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .targetUserId(user.id)
                        .shouldBeModerator(false)
                        .send();

                    return new TwitchHelixChatResult("unmod.success", user.displayName + " is no longer a moderator", Map.of("user", user.displayName));
                }

                /* ------------------------ */
                /* VIPs                     */
                /* ------------------------ */
                case "vips": {
                    List<HelixSimpleUser> vips = new TwitchHelixGetChannelVIPsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .send();

                    if (vips.isEmpty()) {
                        return new TwitchHelixChatResult("vips.none", "There are no VIPs", Map.of());
                    } else {
                        String list = vips
                            .stream()
                            .map((u) -> u.displayName)
                            .collect(Collectors.joining(", "));
                        return new TwitchHelixChatResult(
                            "vips.list",
                            "Channel VIPs: " + list,
                            Map.of("list", list)
                        );
                    }
                }

                case "vip": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /vip {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetVIPStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .targetUserId(user.id)
                        .shouldBeVip(true)
                        .send();

                    return new TwitchHelixChatResult("vip.success", user.displayName + " is now a VIP", Map.of("user", user.displayName));
                }

                case "unvip": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /unvip {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetVIPStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .targetUserId(user.id)
                        .shouldBeVip(false)
                        .send();

                    return new TwitchHelixChatResult("unvip.success", user.displayName + " is no longer a VIP", Map.of("user", user.displayName));
                }

                /* ------------------------ */
                /* Announcements/Pins       */
                /* ------------------------ */
                case "pin": {
//                    if (args.length == 1) {
//                        return new TwitchHelixChatResult("command.arguments", "Command format: /pin {text}", Map.of());
//                    }
//                    String text = message.substring("/pin ".length());
//                    // TODO
//                    return null;
                    return new TwitchHelixChatResult("command.not_yet", "/pin is not yet supported by Twitch's API", Map.of("command", "/pin"));
                }

                case "shoutout": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /shoutout {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSendShoutoutRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .toBroadcasterId(user.id)
                        .moderatorId(asSenderId)
                        .send();

                    return null;
                }

                case "announce": {
                    if (args.length == 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /announce {text}", Map.of());
                    }
                    String text = message.substring("/announce ".length());

                    new TwitchHelixSendAnnouncementRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .message(text)
                        .send();

                    return null;
                }

                /* ------------------------ */
                /* Moderation Actions       */
                /* ------------------------ */
                case "timeout": {
                    if (args.length != 3) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /timeout {user} {seconds}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    int duration = Integer.parseInt(args[2]);
                    new TwitchHelixSetBanStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .targetUserId(user.id)
                        .shouldBeBanned(true)
                        .durationSeconds(duration)
                        .send();

                    return new TwitchHelixChatResult("timeout.success", user.displayName + " has been timed-out", Map.of("user", user.displayName));
                }

                case "ban": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /ban {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetBanStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .targetUserId(user.id)
                        .shouldBeBanned(true)
                        .send();

                    return new TwitchHelixChatResult("ban.success", user.displayName + " has been banned", Map.of("user", user.displayName));
                }

                case "unban": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /unban {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetBanStatusRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .targetUserId(user.id)
                        .shouldBeBanned(false)
                        .send();

                    return new TwitchHelixChatResult("unban.success", user.displayName + " has been unbanned", Map.of("user", user.displayName));
                }

                case "clear": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /clear", Map.of());
                    }

                    new TwitchHelixDeleteChatMessageRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .clearAll()
                        .send();

                    return new TwitchHelixChatResult("clear.success", "Chat has been cleared", Map.of());
                }

                case "delete": { // IRC compat.
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /delete {message id}", Map.of());
                    }

                    new TwitchHelixDeleteChatMessageRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorId(asSenderId)
                        .messageId(args[1])
                        .send();

                    return new TwitchHelixChatResult("clear.success", "Message has been deleted", Map.of());
                }

                /* ------------------------ */
                /* Chat Modes               */
                /* ------------------------ */
                case "slow": {
                    if (args.length > 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /slow [seconds]", Map.of());
                    }

                    if (args.length == 1) {
                        new TwitchHelixUpdateChatSettingsRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .moderatorUserId(asSenderId)
                            .enableSlowMode()
                            .send();
                    } else {
                        int duration = Integer.parseInt(args[1]);
                        new TwitchHelixUpdateChatSettingsRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .moderatorUserId(asSenderId)
                            .enableSlowMode(duration)
                            .send();
                    }
                    return new TwitchHelixChatResult("slow.success", "Slow mode enabled", Map.of());
                }

                case "slowoff": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /slowoff", Map.of());
                    }

                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .disableSlowMode()
                        .send();
                    return new TwitchHelixChatResult("slowoff.success", "Slow mode disabled", Map.of());
                }

                case "followers": {
                    if (args.length > 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /followers [duration]", Map.of());
                    }

                    if (args.length == 1) {
                        new TwitchHelixUpdateChatSettingsRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .moderatorUserId(asSenderId)
                            .enableSlowMode()
                            .send();
                    } else {
                        int duration = Integer.parseInt(args[1]);
                        new TwitchHelixUpdateChatSettingsRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .moderatorUserId(asSenderId)
                            .enableFollowersOnly(duration)
                            .send();
                    }
                    return new TwitchHelixChatResult("followers.success", "Followers-only mode enabled", Map.of());
                }

                case "followersoff": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /followersoff", Map.of());
                    }

                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .disableFollowersOnly()
                        .send();
                    return new TwitchHelixChatResult("followersoff.success", "Followers-only mode disabled", Map.of());
                }

                case "subscribers": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /subscribers", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .enableSubscribersOnly()
                        .send();
                    return new TwitchHelixChatResult("subscribers.success", "Subscribers-only mode enabled", Map.of());
                }

                case "subscribersoff": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /subscribersoff", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .disableSubscribersOnly()
                        .send();
                    return new TwitchHelixChatResult("subscribersoff.success", "Subscribers-only mode disabled", Map.of());
                }

                case "uniquechat": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /uniquechat", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .enableUniqueMessagesMode()
                        .send();
                    return new TwitchHelixChatResult("uniquechat.success", "Unique messages mode enabled", Map.of());
                }

                case "uniquechatoff": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /uniquechatoff", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .disableUniqueMessagesMode()
                        .send();
                    return new TwitchHelixChatResult("uniquechatoff.success", "Unique messages mode disabled", Map.of());
                }

                case "emoteonly": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /emoteonly", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .enableEmoteOnly()
                        .send();
                    return new TwitchHelixChatResult("emoteonly.success", "Emote-only mode enabled", Map.of());
                }

                case "emoteonlyoff": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /emoteonlyoff", Map.of());
                    }
                    new TwitchHelixUpdateChatSettingsRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .moderatorUserId(asSenderId)
                        .disableEmoteOnly()
                        .send();
                    return new TwitchHelixChatResult("emoteonlyoff.success", "Emote-only mode disabled", Map.of());
                }

                /* ------------------------ */
                /* Misc.                    */
                /* ------------------------ */
                case "commercial": {
                    if (args.length > 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /commercial [30-180]", Map.of());
                    }

                    int duration = 30;
                    if (args.length == 2) {
                        duration = Integer.parseInt(args[1]);
                    }

                    new TwitchHelixStartCommercialRequest(auth)
                        .forBroadcasterId(forBroadcasterId)
                        .length(duration)
                        .send();

                    return new TwitchHelixChatResult("commercial.success", "Running a commercial...", Map.of());
                }

                case "raid": {
                    if (args.length != 2) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /raid {user}", Map.of());
                    }
                    HelixUser user = new TwitchHelixGetUsersRequest(auth)
                        .byLogin(args[1])
                        .send()
                        .get(0);

                    new TwitchHelixSetRaidStatusRequest(auth)
                        .fromBroadcasterId(forBroadcasterId)
                        .toBroadcasterId(user.id)
                        .shouldRaid(true)
                        .send();

                    return new TwitchHelixChatResult("raid.success", "Ready to raid " + user.displayName, Map.of("user", user.displayName));
                }

                case "unraid": {
                    if (args.length != 1) {
                        return new TwitchHelixChatResult("command.arguments", "Command format: /unraid", Map.of());
                    }

                    new TwitchHelixSetRaidStatusRequest(auth)
                        .fromBroadcasterId(forBroadcasterId)
                        .toBroadcasterId(null)
                        .shouldRaid(false)
                        .send();

                    return new TwitchHelixChatResult("unraid.success", "Cancelled outgoing raid", Map.of());
                }

                case "marker": {
                    if (args.length == 1) {
                        new TwitchHelixCreateStreamMarkerRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .text(null)
                            .send();
                    } else {
                        String text = message.substring("/marker ".length());
                        new TwitchHelixCreateStreamMarkerRequest(auth)
                            .forBroadcasterId(forBroadcasterId)
                            .text(text)
                            .send();
                    }
                    return new TwitchHelixChatResult("marker.success", "Created VOD marker", Map.of());
                }

                /* ------------------------ */
                /* Unsupported              */
                /* ------------------------ */
                case "restrict":
                case "unrestrict":
                case "goal":
                case "rules":
                case "prediction":
                case "poll":
                case "endpoll":
                case "deletepoll":
                case "requests":
                case "user":
                case "monitor":
                case "unmonitor":
                case "vote":
                case "block":
                case "unblock":
                case "disconnect":
                case "gift":
                case "w":
                    return new TwitchHelixChatResult("command.unsupported", "Unsupported command: " + message, Map.of("command", message));

                default:
                    return new TwitchHelixChatResult("command.unknown", "Unknown command: " + message, Map.of("command", message));
            }
        } catch (Exception e) {
            String reason;
            try {
                reason = Rson.DEFAULT.fromJson(e.getMessage(), JsonObject.class).getString("message");
            } catch (Exception ignored) {
                reason = e.toString();
            }
            return new TwitchHelixChatResult("failed", "Failed to send message or command: " + reason, Map.of("reason", reason));
        } catch (AssertionError e) {
            String reason = e.getMessage();
            return new TwitchHelixChatResult("failed", "Failed to send message or command: " + reason, Map.of("reason", reason));
        }
    }

    /**
     * Inspect the source code of this file to see what the various formats are.
     */
    public static record TwitchHelixChatResult(String format, String defaultFormatted, Map<String, String> params) {

        @Override
        public String toString() {
            return this.defaultFormatted;
        }
    }

}
