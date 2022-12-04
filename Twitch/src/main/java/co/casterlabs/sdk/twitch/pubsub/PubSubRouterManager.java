package co.casterlabs.sdk.twitch.pubsub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;
import lombok.SneakyThrows;

public class PubSubRouterManager {
    private List<PubSubRouter> routers = new ArrayList<>();
    private Map<PubSubListenRequest, PubSubRouter> mapping = new HashMap<>();

    @SneakyThrows
    public synchronized void subscribeTopic(@NonNull PubSubListenRequest request) {
        PubSubRouter chosenRouter = null;

        for (PubSubRouter router : this.routers) {
            if (router.getAvailableSlots() > request.getTopics().size()) {
                chosenRouter = router;
                break;
            }
        }

        if (chosenRouter == null) {
            chosenRouter = new PubSubRouter();
            this.routers.add(chosenRouter);
        }

        chosenRouter.subscribeTopic(request);
        this.mapping.put(request, chosenRouter);
    }

    public synchronized void unsubscribeTopic(@NonNull PubSubListenRequest request) {
        if (request.isUnlistenMode()) {
            PubSubRouter mappedRouter = this.mapping.remove(request);
            if (mappedRouter == null) return; // Ignore.

            mappedRouter.unsubscribeTopic(request);
        } else {
            throw new IllegalStateException("Request is not in unlisten mode.");
        }
    }

}
