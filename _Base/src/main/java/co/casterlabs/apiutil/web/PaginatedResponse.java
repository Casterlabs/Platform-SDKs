package co.casterlabs.apiutil.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class PaginatedResponse<R> implements Iterable<R> {
    private static final Object INITIAL_CURSOR = new Object();

    private final PageFunction<R> nextPage;

    public Optional<R> first() throws ApiAuthException, ApiException, IOException {
        return this.stream().findFirst();
    }

    public List<R> all() throws ApiAuthException, ApiException, IOException {
        return this.stream()
            .collect(Collectors.toList());
    }

    @Override
    public Iterator<R> iterator() {
        return new Iterator<>() {
            private Object cursor = INITIAL_CURSOR;
            private R[] currentPage;
            private volatile int index = 0;

            @SneakyThrows
            @Override
            public boolean hasNext() {
                if (this.currentPage == null || this.index == this.currentPage.length) {
                    if (this.cursor == null) {
                        return false;
                    }

                    Object cursor = this.cursor == INITIAL_CURSOR ? null : this.cursor;
                    Page<R> page = PaginatedResponse.this.nextPage.next(cursor);

                    this.cursor = page.cursor;
                    this.currentPage = page.response;
                    this.index = 0;
                }

                return this.index != this.currentPage.length;
            }

            @Override
            public R next() {
                return this.currentPage[this.index++];
            }
        };
    }

    public Stream<R> stream() {
        return StreamSupport.stream(this.spliterator(), false);
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
