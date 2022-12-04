package co.casterlabs.sdk.dlive.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class DliveUser {
    public static final String GQL_DATA = "avatar,displayname,username,createdAt,livestream{" + DliveLivestream.GQL_DATA + "},followers(first:0){" + DliveList.GQL_DATA + "}";

    private String avatar;
    private String displayname;
    private String username;
    private Instant createdAt;
    private DliveLivestream livestream;
    private DliveList followers;

}
