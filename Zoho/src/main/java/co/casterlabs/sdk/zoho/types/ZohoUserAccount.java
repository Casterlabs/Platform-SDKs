package co.casterlabs.sdk.zoho.types;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class ZohoUserAccount {
    private String lastName;
    private String country;
    private long lastLogin;
    private String accountDisplayName;
    private String role;
    private String gender;
    private boolean activeSyncEnabled;
    private String accountName;
    private String displayName;
    private String mobileNumber;
    private boolean isCustomAdmin;
    private boolean incomingBlocked;
    private String language;
    private boolean isLogoExist;
    private String type;
    private String URI;
    private String primaryEmailAddress;
    private boolean enabled;
    private long mailboxCreationTime;
    private String incomingUserName;
    private List<ZohoEmailAddress> emailAddress;
    private String mailboxStatus;
    private String basicStorage;
    private String encryptedZuid;
    private String lastClient;
    private long allowedStorage;
    private long usedStorage;
    private List<ZohoSendMailDetail> sendMailDetails;
    private float popFetchTime;
    private ZohoAddress address;
    private long userExpiry;
    private boolean popAccessEnabled;
    private boolean spamcheckEnabled;
    private boolean imapAccessEnabled;
    private String timeZone;
    private long accountCreationTime;
    private long zuid;
    private String firstName;
    private String accountId;
    private long sequence;
    private boolean outgoingBlocked;
    private String mailboxAddress;
    private float lastPasswordReset;
    private boolean tfaEnabled;
    private String phoneNumer;
    private boolean status;

}