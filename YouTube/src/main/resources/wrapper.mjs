import { LiveChat } from "youtube-chat";

const ID = process.argv[2];

const liveChat = new LiveChat({ liveId: ID });
let startTime = null;

function sendMessage(type, data = null) {
    console.log(JSON.stringify({ type, data }));
}

liveChat.on("start", () => {
    startTime = new Date();
    sendMessage("start");
});

liveChat.on("end", (reason) => {
    sendMessage("end", { reason });
    process.exit(0);
});

liveChat.on("chat", (chatItem) => {
    const isHistorical = chatItem.timestamp < startTime;
    sendMessage("chat", {...chatItem, isHistorical });
});

liveChat.on("error", (err) => {
    if (err.toString) {
        err = err.toString();
    }

    sendMessage("error", { error: err });
});

liveChat.start();
