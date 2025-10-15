package co.casterlabs.sdk.youtube;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.apiutil.web.QueryBuilder;
import co.casterlabs.apiutil.web.RsonBodyHandler;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.NonNull;

public abstract class YoutubeApiRequest<T> extends AuthenticatedWebRequest<T, YoutubeAuth> {

    public YoutubeApiRequest(@NonNull YoutubeAuth auth) {
        super(auth);
    }

    protected abstract String url();

    protected abstract @Nullable QueryBuilder query();

    protected abstract void validate();

    protected HttpRequest.Builder request() {
        String url = this.url();

        QueryBuilder query = this.query();
        if (query != null) {
            url += "?" + query;
        }

        return HttpRequest
            .newBuilder()
            .uri(URI.create(url));
    }

    protected abstract T deserialize(JsonObject json) throws JsonValidationException, JsonParseException;

    @Override
    protected T execute() throws ApiException, ApiAuthException, IOException {
        this.validate();

        HttpRequest.Builder request = this.request()
            .header("X-Client-Type", "api");

        HttpResponse<JsonObject> response = WebRequest.sendHttpRequest(
            request,
            RsonBodyHandler.of(JsonObject.class),
            this.auth
        );
        JsonObject body = response.body();

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            // Success!
            return this.deserialize(body);
        }

        if (response.statusCode() == 401) {
            throw new ApiAuthException(body.toString());
        } else {
            throw new ApiException(body.toString());
        }
    }

    /* ---------------- */
    /*  Helper Classes  */
    /* ---------------- */

    public static abstract class Delete<T> extends YoutubeApiRequest<T> {

        public Delete(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected Builder request() {
            return super.request()
                .DELETE();
        }

    }

    public static abstract class List<T> extends YoutubeApiRequest<T> {

        public List(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected abstract String url();

    }

    public static abstract class Insert<T> extends PostRequest<T> {
        public Insert(@NonNull YoutubeAuth auth) {
            super(auth);
        }
    }

    public static abstract class Bind<T> extends PostRequest<T> {
        public Bind(@NonNull YoutubeAuth auth) {
            super(auth);
        }
    }

    public static abstract class Update<T> extends PostRequest<T> {
        public Update(@NonNull YoutubeAuth auth) {
            super(auth);
        }
    }

    /* ---------------- */
    /* Ancestor Classes */
    /* ---------------- */

    public static abstract class PostRequest<T> extends YoutubeApiRequest<T> {

        public PostRequest(@NonNull YoutubeAuth auth) {
            super(auth);
        }

        @Override
        protected abstract String url();

        /**
         * Override as-needed.
         */
        protected String contentType() {
            return "application/json";
        }

        protected abstract String body();

        @Override
        protected Builder request() {
            String body = this.body();
            String contentType = this.contentType();

            if (body == null) {
                return super.request()
                    .POST(BodyPublishers.noBody());
            } else {
                return super.request()
                    .POST(BodyPublishers.ofString(body))
                    .header("Content-Type", contentType);
            }
        }

    }

}
