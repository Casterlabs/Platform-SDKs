package co.casterlabs.caffeineapi.realtime.viewers;

import java.util.List;

public interface CaffeineViewersListener {

    default void onOpen() {}

    public void onJoin(Viewer viewer);

    public void onLeave(Viewer viewer);

    public void onViewerlist(List<Viewer> viewers);

    public void onAnonymousCount(int count);

    public void onTotalCount(int count);

    default void onClose() {}

}
