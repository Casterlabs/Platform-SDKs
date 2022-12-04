package co.casterlabs.sdk.zoho.requests;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.sdk.zoho.ZohoHttpUtil;
import co.casterlabs.sdk.zoho.ZohoAuth;
import co.casterlabs.sdk.zoho.ZohoUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ZohoMailSendEmailRequest extends AuthenticatedWebRequest<Void, ZohoAuth> {
    private @NonNull String accountId;
    private @NonNull String fromAddress;
    private @NonNull String toAddress;
    private @Nullable String ccAddress;
    private @Nullable String bccAddress;

    private @NonNull String encoding = "UTF-8";
    private @NonNull String subject;
    private @NonNull @Setter(AccessLevel.NONE) String content;
    private @NonNull @Setter(AccessLevel.NONE) String mailFormat;

    public ZohoMailSendEmailRequest(@NonNull ZohoAuth auth) {
        super(auth);
    }

    public ZohoMailSendEmailRequest setContentsAsHtml(String html) {
        this.mailFormat = "html";
        this.content = html;

        return this;
    }

    public ZohoMailSendEmailRequest setContentsAsPlaintext(String text) {
        this.mailFormat = "plaintext";
        this.content = text;

        return this;
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        if (ZohoUtil.verifyNotNull(this.accountId, this.fromAddress, this.toAddress, this.encoding, this.subject, this.content, this.mailFormat)) {
            String url = String.format("https://mail.zoho.com/api/accounts/%s/messages", this.accountId);

            JsonObject body = new JsonObject();

            body.put("fromAddress", this.fromAddress);
            body.put("toAddress", this.toAddress);

            body.put("encoding", this.encoding);
            body.put("subject", this.subject);
            body.put("content", this.content);
            body.put("mailFormat", this.mailFormat);

            if (this.ccAddress != null) {
                body.put("ccAddress", this.ccAddress);
            }

            if (this.bccAddress != null) {
                body.put("bccAddress", this.bccAddress);
            }

            try (Response response = ZohoHttpUtil.sendHttp(body.toString(), url, null, "application/json", this.auth)) {
                JsonObject json = Rson.DEFAULT.fromJson(response.body().string(), JsonObject.class);

                if (response.isSuccessful()) {
                    return null;
                } else {
                    throw new ApiException(json.toString());
                }
            }
        } else {
            throw new ApiException("Missing required parameter.");
        }
    }

}
