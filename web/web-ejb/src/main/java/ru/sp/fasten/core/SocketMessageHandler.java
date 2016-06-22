package ru.sp.fasten.core;

import com.google.gson.Gson;
import ru.sp.fasten.core.data.Response;
import ru.sp.fasten.core.data.SocketMessage;

import javax.websocket.Session;
import java.lang.reflect.Type;

public abstract class SocketMessageHandler<M> {

    protected final Type messageType;
    protected final Gson gson = new Gson();
    protected final MessageHandlerContext context;

    public abstract Response handle(Session session, String message);

    /**
     * Generics doesn't work in this case
     * We have to create message type manually
     *
     * @return
     */
    protected abstract Type createMessageType();

    protected SocketMessageHandler(final MessageHandlerContext context) {
        this.context = context;
        this.messageType = createMessageType();
    }

    public MessageHandlerContext getContext() {
        return context;
    }

    protected final M getMessageData(final String message) {
        final SocketMessage req = gson.fromJson(message, messageType);
        if (req.getData() != null) {
            return (M) req.getData();
        } else {
            return null;
        }
    }

    protected final String getSequence_id(final String message){
        final SocketMessage req = gson.fromJson(message, messageType);
        return req.getSequence_id();
    }

}
