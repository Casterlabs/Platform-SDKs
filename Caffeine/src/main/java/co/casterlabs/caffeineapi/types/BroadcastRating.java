package co.casterlabs.caffeineapi.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BroadcastRating {
    MATURE("Mature", "M"),
    PG("PG", "PG"),
    UNRATED("Unrated", "UNRATED");

    private String str;
    private @Getter String api;

    @Override
    public String toString() {
        return this.str;
    }

}
