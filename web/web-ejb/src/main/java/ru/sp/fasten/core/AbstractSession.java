package ru.sp.fasten.core;

import javax.websocket.Session;
import java.util.Date;

public class AbstractSession {

    protected final Date createdAt;
    protected Session session;

    public AbstractSession(Session session) {
        this.createdAt = new Date();
        this.session = session;
    }

    public Session getWebsocketSession() {
        return session;
    }

}
