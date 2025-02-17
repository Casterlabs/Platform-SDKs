package co.casterlabs.apiutil.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class PaginatedResponse<R> {
    private static final Object INITIAL_CURSOR = new Object();

    private final PageFunction<R> nextPage;
    private Object cursor = INITIAL_CURSOR;

    public boolean hasNext() {
        return this.cursor != null;
    }

    public Optional<R> first() throws ApiAuthException, ApiException, IOException {
        R[] next = this.next();
        if (next.length == 0) {
            return Optional.empty();
        }
        return Optional.of(next[0]);
    }

    public List<R> all() throws ApiAuthException, ApiException, IOException {
        List<R> list = new LinkedList<>();
        while (this.hasNext()) {
            R[] next = this.next();
            for (R r : next) {
                list.add(r);
            }
        }
        return list;
    }

    public Stream<R> stream() throws ApiException, ApiAuthException, IOException {
        return Stream.generate(new Supplier<>() {
            private R[] current;
            private volatile int index = 0;

            @SneakyThrows
            @Override
            public synchronized R get() {
                if (this.current == null || this.index == this.current.length) {
                    this.current = PaginatedResponse.this.next();
                    this.index = 0;
                }
                return this.current[this.index++];
            }
        });
    }

    public R[] next() throws ApiException, ApiAuthException, IOException {
        Page<R> page = this.nextPage.next(this.cursor == INITIAL_CURSOR ? null : this.cursor);
        this.cursor = page.cursor;
        return page.response;
    }

    @FunctionalInterface
    public static interface PageFunction<R> {
        public Page<R> next(@Nullable Object cursor) throws ApiException, ApiAuthException, IOException;
    }

    @AllArgsConstructor
    public static class Page<R> {
        public final R[] response;
        public final Object cursor;
    }

}
