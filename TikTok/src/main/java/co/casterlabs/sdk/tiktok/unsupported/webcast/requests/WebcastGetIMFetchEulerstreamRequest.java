package co.casterlabs.sdk.tiktok.unsupported.webcast.requests;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
public class WebcastGetIMFetchEulerstreamRequest extends WebRequest<WebcastResponse> {
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * https://github.com/isaackogan/TikTokLive/wiki/All-About-Signatures
     */
    public static String EULERSTREAM_API_URL = "https://tiktok.eulerstream.com";

    private String roomId;
    private String apiKey = "";

    @SuppressWarnings("deprecation")
    @Override
    protected WebcastResponse execute() throws ApiException, ApiAuthException, IOException {
        Map<String, String> query = new HashMap<>();
        query.put("client", "Casterlabs TiktokApiJava");
        query.put("api_key", this.apiKey);
        query.put("room_id", this.roomId);
        query.put("uuc", "1");

        byte[] imResponse = sendHttpRequestBytes(
            new Request.Builder()
                .url(
                    EULERSTREAM_API_URL + "/webcast/fetch?" +
                        query
                            .entrySet()
                            .stream()
                            .map((entry) -> URLEncoder.encode(entry.getKey()) + '=' + URLEncoder.encode(entry.getValue()))
                            .collect(Collectors.joining("&"))
                )
        );

        try {
            return WebcastResponse.parseFrom(imResponse);
        } catch (Throwable t) {
            throw new IOException(new String(imResponse), t);
        }
    }

    private static byte[] sendHttpRequestBytes(@NonNull Request.Builder builder) throws IOException {
        try (Response response = client.newCall(
            builder.build()
        ).execute()) {
            return response.body().bytes();
        }
    }

}
