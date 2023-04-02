package co.casterlabs.sdk.trovo.chat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonClass(exposeAll = true)
public class TrovoSpell {
    private static final Map<String, TrovoSpell> knownSpells = new HashMap<>();

    // Their lack of api support makes me sad, so I am being subtle about it.
    private static final String unknownSpellImage = "https://img.trovo.live/emotes/sad.webp?imageView2/2/format/webp";

    static {
        // This is ugly, IDC.
        try (InputStream in = TrovoSpell.class.getResourceAsStream("/trovoapijava/trovo_spells.json")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int read;
            byte[] buf = new byte[1024];
            while ((read = in.read(buf)) != -1) {
                baos.write(buf, 0, read);
            }

            List<TrovoSpell> spells = Rson.DEFAULT.fromJson(new String(baos.toByteArray(), StandardCharsets.UTF_8), new TypeToken<List<TrovoSpell>>() {
            });

            for (TrovoSpell spell : spells) {
                knownSpells.put(spell.name, spell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TrovoSpellCurrency currency;
    private int cost;
    private String name;
    private String image;

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
            FastLogger.logStatic(LogLevel.WARNING, "Unknown spell: %s", name);
            return new TrovoSpell(TrovoSpellCurrency.MANA, 0, name, unknownSpellImage);
        } else {
            return known;
        }
    }

    public static enum TrovoSpellCurrency {
        MANA,
        ELIXIR;
    }

}
