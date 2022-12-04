package co.casterlabs.sdk.twitch.helix.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class HelixStreamTag {
    @JsonField("tag_id")
    private String id;

    @JsonField("is_auth")
    private boolean isAuto;

    @ToString.Exclude
    private Map<String, String> localizationNames = new HashMap<>();

    @ToString.Exclude
    private Map<String, String> localizationDescriptions = new HashMap<>();;

    @JsonDeserializationMethod("localization_names")
    private void $deserialize_localizationNames(JsonElement json) {
        for (Entry<String, JsonElement> entry : json.getAsObject().entrySet()) {
            this.localizationNames.put(entry.getKey(), entry.getValue().getAsString());
        }

        this.localizationNames = Collections.unmodifiableMap(this.localizationNames);
    }

    @JsonDeserializationMethod("localization_descriptions")
    private void $deserialize_localizationDescriptions(JsonElement json) {
        for (Entry<String, JsonElement> entry : json.getAsObject().entrySet()) {
            this.localizationDescriptions.put(entry.getKey(), entry.getValue().getAsString());
        }

        this.localizationDescriptions = Collections.unmodifiableMap(this.localizationDescriptions);
    }

}
