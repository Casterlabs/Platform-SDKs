package co.casterlabs.sdk.zoho.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ZohoSendMailDetail {
    private String sendMailId;
    private String displayName;
    private String serverName;
    private String signatureId;
    private int serverPort;
    private String userName;
    private String connectionType;
    private String mode;
    private boolean validated;
    private String fromAddress;
    private long smtpConnection;
    private boolean validationRequired;
    private long validationState;
    private boolean status;

}