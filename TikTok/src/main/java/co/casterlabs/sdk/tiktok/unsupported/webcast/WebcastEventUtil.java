package co.casterlabs.sdk.tiktok.unsupported.webcast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.protobuf.ByteString;

import co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastResponse.Message;

public class WebcastEventUtil {

    public static Object parseEvent(Message message) throws IOException {
        try {
            Class<?> protoClazz = Class.forName("co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast." + message.getMethod());
            Method parseMethod = protoClazz.getMethod("parseFrom", ByteString.class);

            return parseMethod.invoke(null, message.getPayload());
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IOException("Unable to parse Protobuf object.", e);
        }
    }

    public static boolean broadcastEvent(Object listener, Object event) throws InvocationTargetException {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(WebcastEventHandler.class)) continue;

            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1) continue;
            if (parameters[0] != event.getClass()) continue;

            try {
                method.invoke(listener, event);
                return true;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new InvocationTargetException(e);
            }
        }
        return false;
    }

}
