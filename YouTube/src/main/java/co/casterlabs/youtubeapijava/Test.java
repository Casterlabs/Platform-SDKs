package co.casterlabs.youtubeapijava;

import java.io.File;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.youtubeapijava.requests.YoutubeGetChannelSnippetRequest;
import co.casterlabs.youtubeapijava.requests.YoutubeGetLiveBroadcastRequest;
import co.casterlabs.youtubeapijava.types.YoutubeChannelSnippet;
import co.casterlabs.youtubeapijava.types.YoutubeLiveBroadcastData;
import co.casterlabs.youtubeapijava.unsupported.chat.YoutubeChatScraper;
import co.casterlabs.youtubeapijava.unsupported.chat.YoutubeScrapeChatItem;
import co.casterlabs.youtubeapijava.unsupported.chat.YoutubeScrapeChatListener;

public class Test {

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        String apiKey = "AIzaSyDmUrcKcniwCpEDILj1Mb_dhXryXi4qOs4";
        String clientId = "943598315026-iej774pmefnil7mgflo7d28jcis59pts.apps.googleusercontent.com";
        String clientSecret = "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";
        String refreshToken = "1//0fVr72JNq5mpGCgYIARAAGA8SNwF-L9IrgjekmTCsjhPQt47LgFQf6AeIwU1jlpUZaP9oVPECWmoCJQJEGQa2LD05FCtP6uqWXYE";
        String redirectUri = "https://casterlabs.co/auth";

//        System.out.println(
//            YoutubeAuth.generateOAuth2Url(clientId, redirectUri, "https://www.googleapis.com/auth/youtube.readonly", "state")
//        );

//        System.out.print(
//            YoutubeAuth.authorize("4/0AX4XfWiWgn-noif3-bR5UZOQECgK-T3B7ZI13tqhx8cjx9lJTXWAcw0GN9GqukzddCnmsg", redirectUri, clientId, clientSecret)
//        );

        YoutubeAuth auth = new YoutubeAuth(apiKey, clientId, clientSecret, refreshToken);

        YoutubeChannelSnippet channel = new YoutubeGetChannelSnippetRequest(auth)
            .mine()
            .send();

        System.out.printf(
            "Authorized as: %s (%s)\n",
            channel.getTitle(),
            channel.getId()
        );

        YoutubeLiveBroadcastData broadcast = new YoutubeGetLiveBroadcastRequest(auth)
            .mine()
            .send();

        System.out.printf(
            "Broadcast: %s (%s)\n",
            broadcast.getSnippet().getTitle(),
            broadcast.getVideoUrl()
        );

        System.out.printf(
            "LiveChat ID: %s\n",
            broadcast.getSnippet().getLiveChatId()
        );

        final String liveChatId = broadcast.getSnippet().getLiveChatId();

        YoutubeChatScraper.setupEnvironment(new File("test"));

        YoutubeChatScraper.listen(liveChatId, new YoutubeScrapeChatListener() {

            @Override
            public void onStart() {
                System.out.println("Start!");
            }

            @Override
            public void onEnd(@Nullable String reason) {
                System.out.printf("End: %s\n", reason);
            }

            @Override
            public void onChat(YoutubeScrapeChatItem item) {
                StringBuilder message = new StringBuilder();

                for (YoutubeScrapeChatItem.ScrapeMessageItem part : item.getMessage()) {
                    if (part.isTextItem()) {
                        message.append(part.getText());
                    } else {
                        message.append(part.getEmojiText());
                    }
                }

                System.out.printf("%s > %s\n", item.getAuthor().getName(), message);
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });

    }

}
