package co.casterlabs.twitchapi.helix.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class HelixCheermote {
    private List<CheermoteTier> tiers;
    private String prefix;
    private String type;

    @JsonField("is_charitable")
    private boolean charitable;

    public CheermoteTier getTier(int value) {
        CheermoteTier possible = null;

        for (CheermoteTier tier : this.tiers) {
            if ((possible != null) && (tier.minBits < possible.minBits)) {
                continue;
            }

            if (value >= tier.minBits) {
                possible = tier;
            }
        }

        return possible;
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class CheermoteTier {
        private String color;
        private String id;

        @JsonField("min_bits")
        private int minBits;

        private CheermoteImages images;

        @JsonField("can_cheer")
        private boolean cheerable;

        @JsonField("show_in_bits_card")
        private boolean inBitsCard;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class CheermoteImages {
        private CheermoteImageSet light;
        private CheermoteImageSet dark;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class CheermoteImageSet {
        @JsonField("static")
        private CheermoteImageSizes still;
        private CheermoteImageSizes animated;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class CheermoteImageSizes {
        @JsonField("1")
        private String tinyImageLink;

        @JsonField("2")
        private String smallImageLink;

        @JsonField("3")
        private String mediumImageLink;

        @JsonField("4")
        private String largeImageLink;

    }

}
