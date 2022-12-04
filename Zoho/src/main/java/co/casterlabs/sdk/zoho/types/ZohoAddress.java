package co.casterlabs.sdk.zoho.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ZohoAddress {
    private String country;
    private String streetAddr;
    private String city;
    private String postalCode;
    private String state;

}