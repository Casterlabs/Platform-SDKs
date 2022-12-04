package co.casterlabs.thetaapijava.realtime.events;

import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.validation.JsonValidate;

public class ThetaChatHello extends ThetaChatMessage {
    private JsonObject metadata; // temporary

    @JsonValidate
    private void $fixfields() {
        this.text = metadata.getString("message");
        this.metadata = null;
    }

}
