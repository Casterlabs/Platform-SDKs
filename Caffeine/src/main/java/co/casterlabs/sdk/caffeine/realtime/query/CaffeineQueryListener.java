package co.casterlabs.sdk.caffeine.realtime.query;

import co.casterlabs.sdk.caffeine.types.CaffeineStage;

public interface CaffeineQueryListener {

    default void onOpen() {}

    public void onStageUpdate(CaffeineStage stage);

    default void onClose(boolean remote) {}

}
