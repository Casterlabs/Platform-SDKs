package co.casterlabs.apiutil.realtime;

public interface WSListener {

    public void onOpen();

    public void onMessage(String raw);

    public void onClose();

}
