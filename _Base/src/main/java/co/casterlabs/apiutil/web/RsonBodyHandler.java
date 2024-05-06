package co.casterlabs.apiutil.web;

import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow.Subscription;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.serialization.JsonParseException;

public class RsonBodyHandler {

    public static <T> BodyHandler<T> of(Class<T> clazz) {
        return of(TypeToken.of(clazz));
    }

    public static <T> BodyHandler<T> of(TypeToken<T> type) {
        return (ResponseInfo responseInfo) -> {
            return new BodySubscriber<T>() {
                private final BodySubscriber<String> stringSubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);

                @Override
                public void onSubscribe(Subscription subscription) {
                    this.stringSubscriber.onSubscribe(subscription);
                }

                @Override
                public void onNext(List<ByteBuffer> item) {
                    this.stringSubscriber.onNext(item);
                }

                @Override
                public void onError(Throwable throwable) {
                    this.stringSubscriber.onError(throwable);
                }

                @Override
                public void onComplete() {
                    this.stringSubscriber.onComplete();
                }

                @Override
                public CompletionStage<T> getBody() {
                    return this.stringSubscriber
                        .getBody()
                        .thenApply((json) -> {
                            if (json == null || json.isEmpty()) {
                                return null;
                            }
                            try {
                                return Rson.DEFAULT.fromJson(json, type);
                            } catch (JsonParseException e) {
                                throw new RuntimeException(e);
                            }
                        });
                }
            };
        };
    }

}
