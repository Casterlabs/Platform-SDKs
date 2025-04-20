package co.casterlabs.apiutil.web;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.unbescape.uri.UriEscape;

import lombok.NonNull;

public class QueryBuilder {
    private List<String> parameters = new LinkedList<>();

    public static QueryBuilder from(String... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("You must supply an even amount of keys so they may be mapped to k=v pairs.");
        }

        QueryBuilder builder = new QueryBuilder();
        for (int i = 0; i < entries.length; i += 2) {
            String key = entries[i];
            String value = entries[i + 1];
            builder.optionalPut(key, value);
        }
        return builder;
    }

    /**
     * Only put()s if the value isn't null.
     */
    public QueryBuilder optionalPut(@NonNull String key, @Nullable Object value) {
        if (value != null) {
            this.put(key, String.valueOf(value));
        }
        return this;
    }

    public QueryBuilder put(@NonNull String key, @NonNull Object value) {
        this.parameters.add(
            UriEscape.escapeUriQueryParam(key) + '=' +
                UriEscape.escapeUriQueryParam(String.valueOf(value))
        );
        return this;
    }

    public QueryBuilder putAllAsSeparateKeys(@NonNull String key, @NonNull Collection<Object> values) {
        for (Object value : values) {
            this.put(key, value);
        }
        return this;
    }

    public QueryBuilder putAllAsSeparateKeys(@NonNull String key, @NonNull Object... values) {
        for (Object value : values) {
            this.put(key, value);
        }
        return this;
    }

    @Override
    public String toString() {
        if (this.parameters.isEmpty()) {
            return "";
        } else {
            return String.join("&", this.parameters);
        }
    }

}
