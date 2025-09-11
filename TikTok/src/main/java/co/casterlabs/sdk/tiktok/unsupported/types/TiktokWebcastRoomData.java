package co.casterlabs.sdk.tiktok.unsupported.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.AllArgsConstructor;
import lombok.ToString;

// TODO actually parse the fields out into objects. This is just a quick hack.

@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class TiktokWebcastRoomData implements TiktokRoomData {
    public final JsonObject raw;

    @Override
    @ToString.Include
    public @Nullable String chatRoomId() {
        JsonElement id = this.raw.get("id_str");
        if (id == null || !id.isJsonString()) return null;

        return id.getAsString();
    }

    @Override
    @ToString.Include
    public boolean isLive() {
        JsonElement status = this.raw.get("status");
        if (status == null || !status.isJsonNumber()) return false;

        switch (status.getAsNumber().intValue()) {
            case 4: // Ended?
                return false;

            case 2:
            default:
//                System.out.println(this.streamInfo.status);
                return true;
        }
    }

    @Override
    @ToString.Include
    public boolean isAgeRestricted() {
        JsonElement ageRestricted = this.raw.get("age_restricted");
        if (ageRestricted == null || !ageRestricted.isJsonObject()) return false;

        return ageRestricted.getAsObject().getBoolean("restricted");
    }

    @Override
    @ToString.Include
    public @Nullable String title() {
        JsonElement title = this.raw.get("title");
        if (title == null || !title.isJsonString()) return null;

        return title.getAsString();
    }

    @Override
    @ToString.Include
    public @Nullable String coverUrl() {
        JsonElement cover = this.raw.get("cover");
        if (cover == null || !cover.isJsonObject()) return null;

        JsonElement urlList = cover.getAsObject().get("url_list");
        if (urlList == null || !urlList.isJsonArray()) return null;

        JsonElement url = urlList.getAsArray().get(0);
        if (url == null || !url.isJsonString()) return null;

        return url.getAsString();
    }

}
