package ru.sp.fasten.rest.endpoints;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.sp.fasten.core.MessageDispatcher;
import ru.sp.fasten.core.data.Response;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Stateless
@ServerEndpoint(value = "/auth")
public class AuthenticateEndpoint {

    private static final Logger LOG = Logger.getLogger(AuthenticateEndpoint.class);

    @Inject
    MessageDispatcher messageDispatcher;
    @Resource
    ManagedExecutorService mes;

    @SuppressWarnings("unchecked")
    @OnMessage
    public void receiveMessage(Session session, String message) {
        Response res = messageDispatcher.dispatch(session, message);
        final Session s = session;
        final String r = new Gson().toJson(res);
        mes.execute(new Runnable() {
            @Override
            public void run() {
                s.getAsyncRemote().sendText(r);
            }
        });

    }

    @OnOpen
    public void onOpen(Session session) {
        LOG.info("onOpen session");

    }

    @OnError
    public void onError(Session session, Throwable e){
        LOG.error("onError",e);
    }

    @OnClose
    public void onClose(CloseReason reason) {
        LOG.info("onClose session");
        if (reason != null) {
            LOG.info(reason.getCloseCode() + ": " + reason.getReasonPhrase());
        }
    }
}
