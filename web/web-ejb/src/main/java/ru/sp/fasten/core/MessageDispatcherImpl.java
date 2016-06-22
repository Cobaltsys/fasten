package ru.sp.fasten.core;

import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ru.sp.fasten.core.data.ErrorData;
import ru.sp.fasten.core.data.Response;
import ru.sp.fasten.core.handler.LoginHandler;
import ru.sp.fasten.ejb.PersistenceService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.websocket.Session;

/**
 * Created by cobalt on 03.09.15.
 */
@Local(MessageDispatcher.class)
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MessageDispatcherImpl extends AbstractMessageDispatcher implements MessageDispatcher {
    private static final Logger logger = Logger.getLogger(MessageDispatcherImpl.class);

    @Resource
    private ManagedExecutorService managedExecutorService;
    @EJB
    private PersistenceService persistenceService;

    @PostConstruct
    private void init() {
        final MessageHandlerContext context = new MessageHandlerContext(managedExecutorService, persistenceService);
        registerMessageHandler("LOGIN_CUSTOMER",new LoginHandler(context));
    }

    @Override
    public Response dispatch(final Session session, final String message) {
        try {
            if (StringUtils.isBlank(message)) {
                return new Response("ERROR", new ErrorData(ErrorData.ERR_JSON, "Error JSON format"));
            }

            final String msgName = getMessageType(message);
            return processByMessageHandler(msgName, session, message);

        } catch (JsonSyntaxException e) {
            return new Response("ERROR", new ErrorData(ErrorData.ERR_JSON, "Error JSON format"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new Response("ERROR", new ErrorData(ErrorData.ERR_SERV, "Unknown error"));
        }
    }
}
