package ru.sp.fasten.core;

import ru.sp.fasten.core.data.Response;

import javax.websocket.Session;

/**
 * Created by cobalt on 03.09.15.
 */
public interface MessageDispatcher {

    public Response dispatch(final Session session, final String message);
}
