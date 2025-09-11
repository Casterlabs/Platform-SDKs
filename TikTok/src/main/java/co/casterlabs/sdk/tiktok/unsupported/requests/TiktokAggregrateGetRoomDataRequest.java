package co.casterlabs.sdk.tiktok.unsupported.requests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.sdk.tiktok.unsupported.TiktokWebSession;
import co.casterlabs.sdk.tiktok.unsupported.types.TiktokRoomData;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true, fluent = true)
public class TiktokAggregrateGetRoomDataRequest extends WebRequest<TiktokRoomData> {
    private static final Impl[] IMPLEMENTATIONS = {
            new Impl() {
                @Override
                TiktokRoomData execute(TiktokAggregrateGetRoomDataRequest req) throws Throwable {
                    return new TiktokWebGetLiveUserRoomRequest(req.session)
                        .byHandle(req.byHandle)
                        .send();
                }
            },
            new Impl() {
                @Override
                TiktokRoomData execute(TiktokAggregrateGetRoomDataRequest req) throws Throwable {
                    return new TiktokWebScrapeRoomRequest(req.session)
                        .byHandle(req.byHandle)
                        .send();
                }
            },
            new Impl() {
                @Override
                TiktokRoomData execute(TiktokAggregrateGetRoomDataRequest req) throws Throwable {
                    return new TiktokWebcastGetUserRoomRequest(req.session)
                        .byHandle(req.byHandle)
                        .failIfOffline(false)
                        .send();
                }
            },
    };

    private final TiktokWebSession session;
    private String byHandle;

    public TiktokAggregrateGetRoomDataRequest(@NonNull TiktokWebSession session) {
        this.session = session;
    }

    @Override
    protected TiktokRoomData execute() throws ApiException, ApiAuthException, IOException {
        List<Throwable> errors = new LinkedList<>();

        Impl[] impls = IMPLEMENTATIONS.clone();
        Arrays.sort(impls, (t1, t2) -> Long.compare(t2.score, t1.score));

        for (Impl impl : impls) {
            try {
                TiktokRoomData result = impl.execute(this);
                impl.score++;
                return result;
            } catch (Throwable t) {
                impl.score--;
                errors.add(t);
            }
        }

        ApiException toThrow = new ApiException("Either user is not live (or not capable of going live) or TikTok is blocking us.");
        errors.forEach(toThrow::addSuppressed);
        throw toThrow;
    }

    private static abstract class Impl {
        // We really don't care about the thread safety here.
        // We just need it to be somewhat sane and track with the actual failure rate.
        volatile long score = 0;

        abstract TiktokRoomData execute(TiktokAggregrateGetRoomDataRequest req) throws Throwable;

    }

}
