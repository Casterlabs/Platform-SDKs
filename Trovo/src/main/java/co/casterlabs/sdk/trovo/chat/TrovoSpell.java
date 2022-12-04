package co.casterlabs.sdk.trovo.chat;

import java.util.HashMap;
import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@JsonClass(exposeAll = true)
public class TrovoSpell {
    private static final Map<String, TrovoSpell> knownSpells = new HashMap<>();

    // Their lack of api support makes me sad, so I am being subtle about it.
    private static final String unknownSpellStaticImage = "https://img.trovo.live/emotes/sad.webp?imageView2/2/format/png";
    private static final String unknownSpellAnimatedImage = "https://img.trovo.live/emotes/sad.webp?imageView2/2/format/webp";

    static {
        // Since Trovo doesn't expose it in the api, this is the workaround of choice.
        // Relevant javascript code to generate this based off of trovo_spells.json:
        /*
        let java = "";
        
        for (let spell of spells) {
            java +=`knownSpells.put("${spell.name}", new TrovoSpell("${spell.name}", TrovoSpellCurrency.${spell.currency}, ${spell.cost}, "${spell.static_image}", "${spell.animated_image}"));\n`;
        }
        
        console.log(java)
         */

        knownSpells.put("Joy", new TrovoSpell("Joy", TrovoSpellCurrency.MANA, 1000, "https://img.trovo.live/imgupload/shop/20210208_lihswp6n7tr.png?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210208_lihswp6n7tr.png?imageView2/2/format/webp"));
        knownSpells.put("Chocolate", new TrovoSpell("Chocolate", TrovoSpellCurrency.ELIXIR, 100, "https://img.trovo.live/imgupload/shop/20210204_5kbiy6cf4ln.webp?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210204_5kbiy6cf4ln.webp?imageView2/2/format/webp"));
        knownSpells.put("Love Letter", new TrovoSpell("Love Letter", TrovoSpellCurrency.ELIXIR, 500, "https://img.trovo.live/imgupload/shop/20210205_grn7g2e7ix.webp?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210205_grn7g2e7ix.webp?imageView2/2/format/webp"));
        knownSpells.put("Cupid Leon", new TrovoSpell("Cupid Leon", TrovoSpellCurrency.ELIXIR, 2000, "https://img.trovo.live/imgupload/shop/20210201_f0vs2h1j9e.webp?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210201_f0vs2h1j9e.webp?imageView2/2/format/webp"));
        knownSpells.put("RISE 2021", new TrovoSpell("RISE 2021", TrovoSpellCurrency.ELIXIR, 5000, "https://img.trovo.live/imgupload/shop/20201230_a0ggwjtmxhg.webp?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20201230_a0ggwjtmxhg.webp?imageView2/2/format/webp"));
        knownSpells.put("St. Patrick", new TrovoSpell("St. Patrick", TrovoSpellCurrency.MANA, 1000, "https://img.trovo.live/imgupload/shop/20210312_3guy27m840u.png?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210312_3guy27m840u.png?imageView2/2/format/webp"));
        knownSpells.put("Bullseye", new TrovoSpell("Bullseye", TrovoSpellCurrency.MANA, 1000, "https://img.trovo.live/imgupload/shop/20210315_a9m7m8c5n1o.png?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210315_a9m7m8c5n1o.png?imageView2/2/format/webp"));
        knownSpells.put("Super Good", new TrovoSpell("Super Good", TrovoSpellCurrency.ELIXIR, 5000, "https://img.trovo.live/imgupload/shop/20210302_pcidahysz1esupergood.webp?imageView2/2/format/png", "https://img.trovo.live/imgupload/shop/20210302_pcidahysz1esupergood.webp?imageView2/2/format/webp"));
        knownSpells.put("Stay Safe", new TrovoSpell("Stay Safe", TrovoSpellCurrency.MANA, 100, "https://static.trovo.live/imgupload/shop/20200421_tyxzwfzebxbhelmet.png?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200421_tyxzwfzebxbhelmet.png?imageView2/2/format/webp"));
        knownSpells.put("On Fire", new TrovoSpell("On Fire", TrovoSpellCurrency.MANA, 500, "https://static.trovo.live/imgupload/shop/20200610_4ig5dz712a20200610_i74sm6yafscannon2.png?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200610_4ig5dz712a20200610_i74sm6yafscannon2.png?imageView2/2/format/webp"));
        knownSpells.put("Rose", new TrovoSpell("Rose", TrovoSpellCurrency.ELIXIR, 5, "https://static.trovo.live/imgupload/shop/20200509_qnxz9b28xqt.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200509_qnxz9b28xqt.webp?imageView2/2/format/webp"));
        knownSpells.put("HYPE", new TrovoSpell("HYPE", TrovoSpellCurrency.ELIXIR, 10, "https://static.trovo.live/imgupload/shop/20200509_gmi66m2kh9f.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200509_gmi66m2kh9f.webp?imageView2/2/format/webp"));
        knownSpells.put("Bravo", new TrovoSpell("Bravo", TrovoSpellCurrency.ELIXIR, 50, "https://static.trovo.live/imgupload/shop/20200509_6kqivntp45r.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200509_6kqivntp45r.webp?imageView2/2/format/webp"));
        knownSpells.put("Winner", new TrovoSpell("Winner", TrovoSpellCurrency.ELIXIR, 100, "https://static.trovo.live/imgupload/shop/20200501_jk9npzs4735winner.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200501_jk9npzs4735winner.webp?imageView2/2/format/webp"));
        knownSpells.put("EZ", new TrovoSpell("EZ", TrovoSpellCurrency.ELIXIR, 300, "https://static.trovo.live/imgupload/shop/20200902_acv79khxt9.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200902_acv79khxt9.webp?imageView2/2/format/webp"));
        knownSpells.put("Shiny Uni", new TrovoSpell("Shiny Uni", TrovoSpellCurrency.ELIXIR, 500, "https://static.trovo.live/imgupload/shop/20200509_9fikzpgc2ne.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200509_9fikzpgc2ne.webp?imageView2/2/format/webp"));
        knownSpells.put("Cash Bang", new TrovoSpell("Cash Bang", TrovoSpellCurrency.ELIXIR, 1000, "https://static.trovo.live/imgupload/shop/20200509_fxbh9hgmtt7.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200509_fxbh9hgmtt7.webp?imageView2/2/format/webp"));
        knownSpells.put("GGWP", new TrovoSpell("GGWP", TrovoSpellCurrency.ELIXIR, 1500, "https://static.trovo.live/imgupload/shop/20200709_jeqeyzmzxk.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200709_jeqeyzmzxk.webp?imageView2/2/format/webp"));
        knownSpells.put("TOP1", new TrovoSpell("TOP1", TrovoSpellCurrency.ELIXIR, 2000, "https://static.trovo.live/imgupload/shop/20200902_8g1v6js5h5ptop1.webp?imageView2/2/format/png", "https://static.trovo.live/imgupload/shop/20200902_8g1v6js5h5ptop1.webp?imageView2/2/format/webp"));
    }

    private String name;
    private TrovoSpellCurrency currency;
    private int cost;
    private String staticImage;
    private String animatedImage;

    public double convertToUSD() {
        // https://trovo.live/support?topicid=C02F691C6E4A05DD%2F8F61C86D1EA7869B
        if (this.currency == TrovoSpellCurrency.ELIXIR) {
            return this.cost / 20.0;
        } else {
            return 0;
        }
    }

    public static TrovoSpell get(String name) {
        TrovoSpell known = knownSpells.get(name);

        if (known == null) {
            return new TrovoSpell(name, TrovoSpellCurrency.UNKNOWN, 0, unknownSpellStaticImage, unknownSpellAnimatedImage);
        } else {
            return known;
        }
    }

    public static enum TrovoSpellCurrency {
        MANA,
        ELIXIR,
        UNKNOWN;

    }

}
