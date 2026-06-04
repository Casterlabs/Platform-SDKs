package co.casterlabs.sdk.fourthwall.platform.requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.PaginatedResponse;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.sdk.fourthwall.FourthwallAuth;
import co.casterlabs.sdk.fourthwall.platform.types.FourthwallDonation;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class FourthwallListDonationsRequest extends AuthenticatedWebRequest<PaginatedResponse<FourthwallDonation>, FourthwallAuth> {

    public FourthwallListDonationsRequest(@NonNull FourthwallAuth auth) {
        super(auth);
    }

    @Override
    protected PaginatedResponse<FourthwallDonation> execute() throws ApiException, ApiAuthException, IOException {
        return new PaginatedResponse<>(new _PaginationHelper<>() {
            @Override
            protected HttpRequest.Builder request(int cursor) {
                QueryBuilder builder = new QueryBuilder()
                    .put("page", cursor)
                    .put("size", 20);

                return HttpRequest.newBuilder()
                    .uri(URI.create("https://api.fourthwall.com/open-api/v1.0/donations?" + builder.toString()))
                    .GET();
            }

            @Override
            protected FourthwallAuth auth() {
                return auth;
            }

            @Override
            protected Class<FourthwallDonation[]> generic() {
                return FourthwallDonation[].class;
            }
        });
    }

}
