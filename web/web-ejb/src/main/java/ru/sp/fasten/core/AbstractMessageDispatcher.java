package ru.sp.fasten.core;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.sp.fasten.core.data.ErrorData;
import ru.sp.fasten.core.data.Response;
import ru.sp.fasten.core.data.SocketMessage;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMessageDispatcher {

    private static final Logger logger = Logger.getLogger(AbstractMessageDispatcher.class);

    protected final Map<String, SocketMessageHandler> messageHandlers = new ConcurrentHashMap<>();
    protected final Gson gson = new Gson();

    protected void registerMessageHandler(final String messageType, final SocketMessageHandler handler) {
        if (messageHandlers.containsKey(messageType)) {
            throw new IllegalStateException("MessageHandler for message '" + messageType + " already registered");
        }
        messageHandlers.put(messageType, handler);
    }

    protected String getMessageType(final String message) {
        final SocketMessage req = gson.fromJson(message, SocketMessage.class);
        return req.getType();
    }

    protected final Response processByMessageHandler(final String messageType, final Session session, final String message) {
        final SocketMessageHandler handler = messageHandlers.get(messageType);
        if (handler == null) {
            return new Response("ERROR", new ErrorData(ErrorData.ERR_FUNC, "Unknown request"));
        }

        final Response result = handler.handle(session, message);
        return result;
    }
}
