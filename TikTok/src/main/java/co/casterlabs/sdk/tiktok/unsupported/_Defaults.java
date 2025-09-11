package co.casterlabs.sdk.tiktok.unsupported;

import java.util.concurrent.ThreadLocalRandom;

import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession.Device;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession.Location;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession.Screen;

//This class is based off of: https://github.com/zerodytrash/TikTok-Live-Connector/blob/1f0b08c955b81d4fdb8be7fb7519abc2ba723a6f/src/lib/config.ts#L207

class _Defaults {
    private static final Location[] LOCATIONS = {
            // We're leaving out US-based locations because of
            // side effects from the US TikTok ban.
            new Location("en-ZA", "en", "ZA", "Africa/Johannesburg"),

            new Location("en-CA", "en", "CA", "America/Montreal"),
            new Location("en-CA", "en", "CA", "America/Toronto"),
            new Location("en-CA", "en", "CA", "America/Vancouver"),

            new Location("en-AU", "en", "AU", "Australia/Sydney"),
            new Location("en-AU", "en", "AU", "Australia/Melbourne"),
            new Location("en-AU", "en", "AU", "Australia/Brisbane"),
            new Location("en-AU", "en", "AU", "Australia/Adelaide"),
            new Location("en-AU", "en", "AU", "Australia/Perth"),

            new Location("en-GB", "en", "GB", "Europe/London"),

            new Location("en-NZ", "en", "NZ", "Pacific/Auckland"),
    };

    private static final Screen[] SCREENS = {
            // https://gs.statcounter.com/screen-resolution-stats/desktop/worldwide
            new Screen(1920, 1080),
            new Screen(1536, 864),
            new Screen(1366, 768),
            new Screen(1280, 720),
            new Screen(1440, 900),
            // skip 800, 600
            new Screen(2560, 1440),
            new Screen(1600, 900),
            new Screen(1280, 960),
    };

    private static final Device[] DEVICES = {
            // Cherry picking the "most likely" UAs.

            // https://www.whatismybrowser.com/guides/the-latest-user-agent/chrome
            new Device("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36"),
            new Device("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36"),

            // https://www.whatismybrowser.com/guides/the-latest-user-agent/firefox
            new Device("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:142.0) Gecko/20100101 Firefox/142.0"),
            new Device("Mozilla/5.0 (Macintosh; Intel Mac OS X 15.6; rv:142.0) Gecko/20100101 Firefox/142.0"),
            new Device("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:142.0) Gecko/20100101 Firefox/142.0"),

            // https://www.whatismybrowser.com/guides/the-latest-user-agent/safari
            new Device("Mozilla/5.0 (Macintosh; Intel Mac OS X 15_6_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.6 Safari/605.1.15"),

            // https://www.whatismybrowser.com/guides/the-latest-user-agent/edge
            new Device("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36 Edg/140.0.3485.54"),

            // https://www.whatismybrowser.com/guides/the-latest-user-agent/opera
            new Device("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36 OPR/121.0.0.0"),
    };

    static Location randomLocation() {
        return random(LOCATIONS);
    }

    static Screen randomScreen() {
        return random(SCREENS);
    }

    static Device randomDevice() {
        return random(DEVICES);
    }

    private static <T> T random(T[] arr) {
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }
}
