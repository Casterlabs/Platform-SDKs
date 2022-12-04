package co.casterlabs.caffeineapi.realtime.query;

import co.casterlabs.caffeineapi.types.CaffeineStage;

public interface CaffeineQueryListener {

    default void onOpen() {}

    public void onStageUpdate(CaffeineStage stage);

    default void onClose(boolean remote) {}

}
