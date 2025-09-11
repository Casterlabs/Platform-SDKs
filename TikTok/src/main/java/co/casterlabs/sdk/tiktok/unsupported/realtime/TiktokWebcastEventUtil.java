package co.casterlabs.sdk.tiktok.unsupported.realtime;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jetbrains.annotations.Nullable;

import com.google.protobuf.ByteString;

import co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast.BaseProtoMessage;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class TiktokWebcastEventUtil {

    public static @Nullable Object parseEvent(BaseProtoMessage message) throws IOException {
        try {
            Class<?> protoClazz = Class.forName("co.casterlabs.sdk.tiktok.unsupported.types.protobuf.webcast." + message.getType());
            Method parseMethod = protoClazz.getMethod("parseFrom", ByteString.class);

            return parseMethod.invoke(null, message.getPayload());
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            FastLogger.logStatic(LogLevel.DEBUG, "Unable to parse Protobuf object\n%s", e);
            return null;
        }
    }

    public static boolean broadcastEvent(Object listener, Object event) throws InvocationTargetException {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(TiktokWebcastEventHandler.class)) continue;

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
