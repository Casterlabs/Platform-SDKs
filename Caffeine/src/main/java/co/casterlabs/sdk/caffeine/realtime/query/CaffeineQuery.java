package co.casterlabs.sdk.caffeine.realtime.query;

import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.protocols.Protocol;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.sdk.caffeine.CaffeineApi;
import co.casterlabs.sdk.caffeine.CaffeineAuth;
import co.casterlabs.sdk.caffeine.CaffeineEndpoints;
import co.casterlabs.sdk.caffeine.types.CaffeineStage;
import co.casterlabs.sdk.caffeine.types.CaffeineUser;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class CaffeineQuery implements Closeable {
    private static final Draft_6455 DRAFT = new Draft_6455(Collections.emptyList(), Collections.singletonList(new Protocol("graphql-ws")));
    private static URI URI;
//    private static final String query = "{\"id\":\"1\",\"type\":\"start\",\"payload\":{\"variables\":{\"clientId\":\"Anonymous\",\"clientType\":\"WEB\",\"constrainedBaseline\":false,\"username\":\"%USERNAME%\",\"viewerStreams\":[]},\"extensions\":{},\"operationName\":\"Stage\",\"query\":\"subscription Stage($clientId: ID!, $clientType: ClientType!, $constrainedBaseline: Boolean, $username: String!, $viewerStreams: [StageSubscriptionViewerStreamInput!]) {\\n  stage(clientId: $clientId, clientType: $clientType, clientTypeForMetrics: \\\"WEB\\\", constrainedBaseline: $constrainedBaseline, username: $username, viewerStreams: $viewerStreams) {\\n    error {\\n      __typename\\n      title\\n      message\\n    }\\n    stage {\\n      id\\n      username\\n      title\\n      broadcastId\\n      contentRating\\n      live\\n      feeds {\\n        id\\n        clientId\\n        clientType\\n        gameId\\n        liveHost {\\n          __typename\\n          ... on LiveHostable {\\n            address\\n          }\\n          ... on LiveHosting {\\n            address\\n            volume\\n            ownerId\\n            ownerUsername\\n          }\\n        }\\n        sourceConnectionQuality\\n        capabilities\\n        role\\n        restrictions\\n        stream {\\n          __typename\\n          ... on BroadcasterStream {\\n            id\\n            sdpAnswer\\n            url\\n          }\\n          ... on ViewerStream {\\n            id\\n            sdpOffer\\n            url\\n          }\\n        }\\n      }\\n    }\\n  }\\n}\\n\"}}";
//    private static final String auth = "{\"type\":\"connection_init\",\"payload\":{\"X-Credential\":\"%CREDENTIAL%\"}}";
    private static final String query = "{\"id\":\"1\",\"type\":\"start\",\"payload\":{\"variables\":{\"clientId\":\"casterlabs.caffeineapijava\",\"clientType\":\"WEB\",\"constrainedBaseline\":false,\"username\":\"%USERNAME%\",\"viewerStreams\":[],\"stageSource\":\"STAGE\"},\"extensions\":{},\"operationName\":\"Stage\",\"query\":\"subscription Stage($clientId: ID!, $clientType: ClientType!, $constrainedBaseline: Boolean, $username: String!, $viewerStreams: [StageSubscriptionViewerStreamInput!], $stageSource: StageSource) {\\n  stage(clientId: $clientId, clientType: $clientType, clientTypeForMetrics: \\\"WEB\\\", constrainedBaseline: $constrainedBaseline, username: $username, viewerStreams: $viewerStreams, stageSource: $stageSource) {\\n    error {\\n      __typename\\n      title\\n      message\\n    }\\n    stage {\\n      id\\n      username\\n      title\\n      broadcastId\\n      contentRating\\n      live\\n      badge\\n      hlsUrl\\n      trailerUrl\\n      viewingOptions\\n      currentViewingOption\\n      reactionsDisabled\\n      feeds {\\n        id\\n        clientId\\n        clientType\\n        gameId\\n        liveHost {\\n          __typename\\n          ... on LiveHostable {\\n            address\\n          }\\n          ... on LiveHosting {\\n            address\\n            volume\\n            ownerId\\n            ownerUsername\\n          }\\n        }\\n        sourceConnectionQuality\\n        capabilities\\n        role\\n        restrictions\\n        stream {\\n          __typename\\n          ... on BroadcasterStream {\\n            id\\n            sdpAnswer\\n            url\\n          }\\n          ... on ViewerStream {\\n            id\\n            sdpOffer\\n            url\\n          }\\n        }\\n      }\\n    }\\n  }\\n}\\n\"}}";
    private static final String auth = "{\"type\":\"connection_init\",\"payload\":{\"X-Credential\":\"%CREDENTIAL%\",\"X-Caffeine-Vendor-Identifier\":\"casterlabs.caffeineapijava\"}}";

    private @Setter @Nullable CaffeineQueryListener listener;

    private final String username;
    private Connection conn;

    static {
        try {
            URI = new URI(CaffeineEndpoints.QUERY);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public CaffeineQuery(CaffeineUser user) {
        this(user.getUsername());
    }

    public CaffeineQuery(String username) {
        this.username = username;
    }

    public void connect() {
        if (this.conn == null) {
            this.conn = new Connection();
            this.conn.connect();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public void connectBlocking() throws InterruptedException {
        if (this.conn == null) {
            this.conn = new Connection();
            this.conn.connectBlocking();
        } else {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        }
    }

    public boolean isOpen() {
        return this.conn != null;
    }

    @Override
    public void close() {
        doCleanup();
    }

    private void doCleanup() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception ignored) {}
        }

        this.conn = null;
    }

    private class Connection extends WebSocketClient {

        public Connection() {
            super(URI, DRAFT);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            this.send(auth.replace("%CREDENTIAL%", CaffeineAuth.getAnonymousCredential()));
            this.send(query.replace("%USERNAME%", username).replace("\n", "\\n"));

            if (listener != null) {
                listener.onOpen();
            }
        }

        @Override
        public void onMessage(String raw) {
            try {
                if (listener == null) {
                    return; // User hasn't added a listener yet, it's not worth processing.
                }

                JsonObject message = CaffeineApi.RSON.fromJson(raw, JsonObject.class);

                if (message.get("type").getAsString().equalsIgnoreCase("data")) {
                    JsonObject payload = message.getObject("payload");

                    if (payload.get("data").isJsonNull()) {
                        throw new ApiException(payload.getArray("errors").toString());
                    }

                    JsonObject stageData = payload
                        .getObject("data")
                        .getObject("stage")
                        .getObject("stage");

                    CaffeineStage stage = CaffeineStage.fromJson(stageData);

                    listener.onStageUpdate(stage);
                }
            } catch (Throwable t) {
                FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred whilst processing frame: %s\n%s", raw, t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            doCleanup();

            if (listener != null) {
                new Thread(() -> listener.onClose(remote));
            }
        }

        @Override
        public void onError(Exception e) {
            FastLogger.logStatic(LogLevel.SEVERE, "An uncaught exception occurred:\n%s", e);
        }

        @Override
        public void send(String payload) {
            payload = payload.replace("\n", "");
            super.send(payload);
        }

    }

}
