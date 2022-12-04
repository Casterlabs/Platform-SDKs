package co.casterlabs.caffeineapi.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserBadge {
    NONE("https://raw.githubusercontent.com/Casterlabs/CaffeineApiJava/master/badges/none.png"),
    CASTER("https://raw.githubusercontent.com/Casterlabs/CaffeineApiJava/master/badges/caster.png"),
    CYAN("https://raw.githubusercontent.com/Casterlabs/CaffeineApiJava/master/badges/cyan.png"),
    VERIFIED("https://raw.githubusercontent.com/Casterlabs/CaffeineApiJava/master/badges/verified.png"),
    UNKNOWN("https://raw.githubusercontent.com/Casterlabs/CaffeineApiJava/master/badges/none.png");

    private @Getter String imageLink;

    public static UserBadge from(String str) {
        if (str == null) {
            return NONE;
        } else {
            switch (str) {
                case "CASTER":
                    return CASTER;

                case "PARTNER1":
                    return CYAN;

                // Caffeine has repurposed these badges for something else entirely.
                // However, I am not going to include them because they're probably
                // going to change them again in the future anyways ¯\_(ツ)_/¯

                // case "PARTNER2":

                // case "PARTNER3":

                case "VERIFIED":
                    return VERIFIED;

                default:
                    return UNKNOWN;
            }
        }
    }

}
