package co.casterlabs.apiutil.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;

public class ParsedQuery implements Map<String, List<String>> {
    private Map<String, List<String>> map;

    public final String raw;

    private ParsedQuery(Map<String, List<String>> map, String raw) {
        this.map = map;
        this.raw = raw;
    }

    public static ParsedQuery from(@NonNull String src) {
        if (src.startsWith("?")) {
            src = src.substring(1);
        }

        return new ParsedQuery(
            Arrays // Magic.
                .stream(src.split("&"))
                .map((it) -> {
                    try {
                        int eqIdx = it.indexOf("=");

                        if (eqIdx == -1) {
                            return new SimpleImmutableEntry<>(
                                URLDecoder.decode(it, "UTF-8"),
                                ""
                            );
                        }

                        String key = it.substring(0, eqIdx);
                        String value = it.substring(eqIdx + 1);

                        return new SimpleImmutableEntry<>(
                            URLDecoder.decode(key, "UTF-8"),
                            URLDecoder.decode(value, "UTF-8")
                        );
                    } catch (UnsupportedEncodingException ignored) {
                        return null;
                    }
                })
                .filter((e) -> e != null)
                .collect(
                    Collectors.groupingBy(
                        SimpleImmutableEntry::getKey,
                        HashMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                    )
                ),
            src
        );
    }

    /* ---------------- */
    /* Supported        */
    /* ---------------- */

    @Override
    public String toString() {
        return this.raw;
    }

    public String getSingle(String key) {
        List<String> values = this.get(key);
        if (values == null) {
            return null;
        } else {
            return values.get(0);
        }
    }

    public String getSingleOrDefault(String key, String defaultValue) {
        List<String> values = this.get(key);
        if (values == null) {
            return defaultValue;
        } else {
            return values.get(0);
        }
    }

    @Override
    public List<String> get(Object key) {
        return this.map.get(String.valueOf(key).toLowerCase());
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(String.valueOf(key).toLowerCase());
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<List<String>> values() {
        return this.map.values();
    }

    /* ---------------- */
    /* Unsupported      */
    /* ---------------- */

    @Override
    public List<String> put(String key, List<String> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

}
