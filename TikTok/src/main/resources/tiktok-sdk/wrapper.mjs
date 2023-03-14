import { WebcastPushConnection } from "tiktok-live-connector";

const tiktokUsername = process.argv[2];
const tiktokLiveConnection = new WebcastPushConnection(tiktokUsername, {
    processInitialData: false,
    enableExtendedGiftInfo: false,
});

/* ---------------- */
/* Helpers          */
/* ---------------- */

function sendMessage(type, data = null) {
    console.log(JSON.stringify({ type, data }));
}

function deriveUser(event) {
    const roles = [];

    switch (event.followRole) {
        case 1: {
            roles.push("FOLLOWER");
            break;
        }
        case 2: {
            roles.push("FRIENDS");
            break;
        }
    }

    if (event.isModerator) {
        roles.push("MODERATOR");
    }

    if (event.isSubscriber) {
        roles.push("SUBSCRIBER");
    }

    const badges = [];

    for (const userBadge of event.userBadges) {
        if (userBadge.type == "image") {
            badges.push(userBadge.url);
        }
    }

    return {
        handle: event.uniqueId,
        displayName: event.nickname,
        avatarUrl: event.profilePictureUrl,
        roles: roles,
        badges: badges,
    };
}

/* ---------------- */
/* Events           */
/* ---------------- */

tiktokLiveConnection.on("member", (event) => {
    sendMessage("join", {
        user: deriveUser(event),
    });
});

tiktokLiveConnection.on("chat", (event) => {
    sendMessage("chat", {
        user: deriveUser(event),
        message: event.comment,
    });
});

tiktokLiveConnection.on("gift", (event) => {
    if (event.giftType == 1 && event.repeatEnd) {
        // Streak ended, we've already sent out the streak items though.
        return;
    }

    sendMessage("gift", {
        user: deriveUser(event),
        gift: {
            id: event.giftId,
            name: event.giftName,
            imageUrl: event.giftPictureUrl,
            diamondValue: event.diamondCount,
            coinValue: event.diamondCount * 2,
            usdValue: (event.diamondCount * 0.05) / 2,
        },
        isStreak: event.giftType == 1,
    });
});

tiktokLiveConnection.on("roomUser", (event) => {
    sendMessage("viewers", {
        count: event.viewerCount,
    });
});

tiktokLiveConnection.on("like", (event) => {
    sendMessage("like", {
        user: deriveUser(event),
        likeCount: event.likeCount,
        roomTotal: event.totalLikeCount,
    });
});

tiktokLiveConnection.on("follow", (event) => {
    sendMessage("follow", {
        user: deriveUser(event),
    });
});

tiktokLiveConnection.on("share", (event) => {
    sendMessage("share", {
        user: deriveUser(event),
    });
});

tiktokLiveConnection.on("emote", (event) => {
    sendMessage("sticker", {
        user: deriveUser(event),
        sticker: {
            id: event.emoteId,
            imageUrl: event.emoteImageUrl,
        },
    });
});

tiktokLiveConnection.on("envelope", (event) => {
    sendMessage("treasure_chest", {
        user: deriveUser(event),
        chest: {
            coinValue: event.coins,
            diamondValue: event.coins / 2,
            usdValue: ((event.coins / 2) * 0.05) / 2,
        },
    });
});

tiktokLiveConnection.on("questionNew", (event) => {
    sendMessage("question", {
        user: deriveUser(event),
        message: event.questionText,
    });
});

tiktokLiveConnection.on("subscribe", (event) => {
    sendMessage("subscription", {
        user: deriveUser(event),
    });
});

/* ---------------- */
/* Life Cycle       */
/* ---------------- */

tiktokLiveConnection.on("disconnected", (reason) => {
    sendMessage("end", { reason: reason.toString() });
    process.exit(0);
});

tiktokLiveConnection
    .connect()
    .then(() => {
        sendMessage("start");
    })
    .catch((err) => {
        sendMessage("end", { reason: err.toString() });
        process.exit(0);
    });
