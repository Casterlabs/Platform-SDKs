package co.casterlabs.apiutil.web;

import java.util.LinkedList;
import java.util.List;

import org.unbescape.uri.UriEscape;

import lombok.NonNull;

public class URIParameters {
    private List<String> parameters = new LinkedList<>();

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
