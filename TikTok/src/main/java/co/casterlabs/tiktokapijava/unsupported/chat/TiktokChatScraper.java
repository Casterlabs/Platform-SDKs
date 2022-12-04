package co.casterlabs.tiktokapijava.unsupported.chat;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.Scanner;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeChatEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeFollowEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeGiftEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeLikeEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeShareEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeStickerEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeSubscriptionEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeTreasureChestEvent;
import co.casterlabs.tiktokapijava.unsupported.chat.types.TiktokScrapeViewersEvent;
import lombok.NonNull;

public class TiktokChatScraper {
    private static final boolean isRunningOnWindows = System.getProperty("os.name", "").contains("Windows");
    private static File targetLocation = null;

    public static Closeable listen(@NonNull String handle, @NonNull TiktokScrapeChatListener listener) throws IOException {
        assert targetLocation != null : "You must call setupEnvironment() before calling listen().";

        Process process = execute("node", "wrapper.mjs", handle);

        Thread readThread = new Thread(() -> {
            try (Scanner in = new Scanner(process.getInputStream())) {
                while (in.hasNext()) {
                    JsonObject packet = Rson.DEFAULT.fromJson(in.nextLine(), JsonObject.class);
                    String type = packet.getString("type");

                    System.out.println(packet);

                    switch (type) {

                        // Lifecycle

                        case "start": {
                            listener.onStart();
                            break;
                        }

                        case "end": {
                            JsonObject data = packet.getObject("data");
                            String reason = data.getString("reason");

                            listener.onEnd(reason);
                            break;
                        }

                        // Events

                        case "chat": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeChatEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeChatEvent.class);

                            listener.onChat(event);
                            break;
                        }

                        case "gift": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeGiftEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeGiftEvent.class);

                            listener.onGift(event);
                            break;
                        }

                        case "viewers": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeViewersEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeViewersEvent.class);

                            listener.onViewers(event);
                            break;
                        }

                        case "like": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeLikeEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeLikeEvent.class);

                            listener.onLike(event);
                            break;
                        }

                        case "follow": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeFollowEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeFollowEvent.class);

                            listener.onFollow(event);
                            break;
                        }

                        case "share": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeShareEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeShareEvent.class);

                            listener.onShare(event);
                            break;
                        }

                        case "sticker": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeStickerEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeStickerEvent.class);

                            listener.onSticker(event);
                            break;
                        }

                        case "treasure_chest": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeTreasureChestEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeTreasureChestEvent.class);

                            listener.onTreasureChest(event);
                            break;
                        }

                        case "question": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeChatEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeChatEvent.class);

                            listener.onQuestion(event);
                            break;
                        }

                        case "subscription": {
                            JsonObject data = packet.getObject("data");
                            TiktokScrapeSubscriptionEvent event = Rson.DEFAULT.fromJson(data, TiktokScrapeSubscriptionEvent.class);

                            listener.onSubscription(event);
                            break;
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        readThread.setName("TikTokScrapeChat Read Thread: " + handle);
        readThread.start();

        return () -> {
            process.destroy();
            listener.onEnd("Closed by user");
        };
    }

    public static void setupEnvironment(@NonNull File targetLocation) throws IOException, InterruptedException {
        TiktokChatScraper.targetLocation = targetLocation;
        targetLocation.mkdirs();

        final File wrapperFile = new File(targetLocation, "wrapper.mjs");
        wrapperFile.delete();

        execute("npm", "i", "tiktok-live-connector").waitFor();

        Files.copy(
            TiktokChatScraper.class.getClassLoader().getResourceAsStream("wrapper.mjs"),
            wrapperFile.toPath()
        );

    }

    private static Process execute(String... cmd) throws IOException {
        String[] commandLine;

        if (isRunningOnWindows) {
            commandLine = new String[] {
                    "cmd",
                    "/c",
                    String.join(" ", cmd)
            };
        } else {
            commandLine = new String[] {
                    "bash",
                    "-c",
                    String.join(" ", cmd)
            };
        }

        return new ProcessBuilder(commandLine)
            .directory(targetLocation)
            .redirectError(Redirect.INHERIT)
            .redirectInput(Redirect.PIPE) // Unused.
            .redirectOutput(Redirect.PIPE)
            .start();
    }

}
