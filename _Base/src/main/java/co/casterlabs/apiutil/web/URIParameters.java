package co.casterlabs.apiutil.web;

import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.unbescape.uri.UriEscape;

import lombok.NonNull;

public class URIParameters {
    private List<String> parameters = new LinkedList<>();

    /**
     * Only put()s if the value isn't null.
     */
    public URIParameters optionalPut(@NonNull String key, @Nullable String value) {
        if (value != null) {
            this.put(key, value);
        }
        return this;
    }

    public URIParameters put(@NonNull String key, @NonNull String value) {
        this.parameters.add(
            UriEscape.escapeUriQueryParam(key) + '=' +
                UriEscape.escapeUriQueryParam(value)
        );
        return this;
    }

    public URIParameters putAllAsSeparateKeys(@NonNull String key, @NonNull List<String> values) {
        for (String value : values) {
            this.put(key, value);
        }
        return this;
    }

    public URIParameters putAllAsSeparateKeys(@NonNull String key, @NonNull String[] values) {
        for (String value : values) {
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
