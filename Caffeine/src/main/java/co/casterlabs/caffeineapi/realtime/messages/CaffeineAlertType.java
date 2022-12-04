package co.casterlabs.caffeineapi.realtime.messages;

import co.casterlabs.rakurai.json.element.JsonElement;

public enum CaffeineAlertType {
    REACTION,
    DIGITAL_ITEM,
    SHARE,
    FOLLOW,
    UNKNOWN;

    public static CaffeineAlertType fromJson(JsonElement element) {
        switch (element.getAsString().toUpperCase()) {
            case "REACTION":
                return REACTION;

            case "DIGITAL_ITEM":
                return DIGITAL_ITEM;

            case "RESCIND": // ?
                return UNKNOWN;

            case "SHARE":
                return SHARE;

            case "FOLLOW":
                return FOLLOW;

            default:
                return UNKNOWN;
        }
    }

}
