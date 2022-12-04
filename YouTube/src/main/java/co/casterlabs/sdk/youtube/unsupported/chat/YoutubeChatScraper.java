package co.casterlabs.sdk.youtube.unsupported.chat;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.Scanner;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.youtube.YoutubeApi;
import lombok.NonNull;

public class YoutubeChatScraper {
    private static final boolean isRunningOnWindows = System.getProperty("os.name", "").contains("Windows");
    private static File targetLocation = null;

    public static Closeable listen(@NonNull String videoId, @NonNull YoutubeScrapeChatListener listener) throws IOException {
        assert targetLocation != null : "You must call setupEnvironment() before calling listen().";

        Process process = execute("node", "wrapper.mjs", videoId);

        Thread readThread = new Thread(() -> {
            try (Scanner in = new Scanner(process.getInputStream())) {
                while (in.hasNext()) {
                    JsonObject packet = Rson.DEFAULT.fromJson(in.nextLine(), JsonObject.class);
                    String type = packet.getString("type");

                    switch (type) {
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

                        case "chat": {
                            JsonObject data = packet.getObject("data");
                            YoutubeScrapeChatItem item = YoutubeApi.RSON.fromJson(data, YoutubeScrapeChatItem.class);

                            listener.onChat(item);
                            break;
                        }

                        case "error": {
                            JsonObject data = packet.getObject("data");
                            String error = data.getString("error");

                            if (error.equals("Error: Request failed with status code 404")) {
                                process.destroy();
                                // Stream is over.
                                listener.onEnd("Stream is over");
                            } else if (error.equals("Error: Live Stream was not found")) {
                                process.destroy();
                                // Stream is over.
                                listener.onEnd("No stream");
                            } else {
                                listener.onError(error);
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        readThread.setName("ScrapeChat Read Thread: " + videoId);
        readThread.start();

        return () -> {
            process.destroy();
            listener.onEnd("Closed by user");
        };
    }

    public static void setupEnvironment(@NonNull File targetLocation) throws IOException, InterruptedException {
        YoutubeChatScraper.targetLocation = targetLocation;
        targetLocation.mkdirs();

        final File wrapperFile = new File(targetLocation, "wrapper.mjs");
        wrapperFile.delete();

        execute("npm", "i", "youtube-chat").waitFor();

        Files.copy(
            YoutubeChatScraper.class.getClassLoader().getResourceAsStream("wrapper.mjs"),
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
