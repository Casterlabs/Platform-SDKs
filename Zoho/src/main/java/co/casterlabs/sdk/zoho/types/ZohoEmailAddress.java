package co.casterlabs.sdk.zoho.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ZohoEmailAddress {
    private boolean isAlias;
    private boolean isPrimary;
    private String mailId;
    private boolean isConfirmed;

}