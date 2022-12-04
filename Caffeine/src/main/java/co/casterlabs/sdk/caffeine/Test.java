package co.casterlabs.sdk.caffeine;

import java.io.File;
import java.nio.file.Files;

import co.casterlabs.sdk.caffeine.requests.CaffeineBroadcastInfoRequest;
import co.casterlabs.sdk.caffeine.requests.CaffeineUpdateBroadcastInfoRequest;
import co.casterlabs.sdk.caffeine.requests.CaffeineUserInfoRequest;
import co.casterlabs.sdk.caffeine.types.BroadcastRating;
import co.casterlabs.sdk.caffeine.types.CaffeineBroadcast;
import co.casterlabs.sdk.caffeine.types.CaffeineUser;

public class Test {

    public static void main(String[] args) throws Exception {
//        FastLoggingFramework.setDefaultLevel(LogLevel.TRACE);

        CaffeineAuth auth = new CaffeineAuth();
        auth.login("bde1d34d666d937ab4e46eb20946e58d1699a8252020e4d038d8a2074ef87598.CAID89779184B82E44EF941DC5478B3FBAE3");

        CaffeineUser user = new CaffeineUserInfoRequest()
            .setUsername("itzlcyx")
            .send();

        CaffeineBroadcast broadcast = new CaffeineBroadcastInfoRequest()
            .setBroadcastId(user.getBroadcastID())
            .send();

        byte[] thumbnail = Files.readAllBytes(new File("C:\\Users\\pigal\\Pictures\\schnoze.jpg").toPath());

        new CaffeineUpdateBroadcastInfoRequest(auth)
            .setBroadcast(broadcast)
            .setTitle("testing shit.")
            .setRating(BroadcastRating.MATURE)
            .setThumbnailBytes(thumbnail)
            .send();

//        System.out.println(user);
//        System.out.println(broadcast.getName());

    }

}
