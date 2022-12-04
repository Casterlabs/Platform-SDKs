package co.casterlabs.sdk.tiktok.unsupported.chat.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class TiktokScrapeUser {
    private String handle;
    private String displayName;
    private String avatarUrl;
    private List<String> roles;
    private List<String> badges;

}
