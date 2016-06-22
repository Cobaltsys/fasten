package ru.sp.fasten.core.handler;

import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import ru.sp.fasten.core.MessageHandlerContext;
import ru.sp.fasten.core.SocketMessageHandler;
import ru.sp.fasten.core.data.*;

import javax.websocket.Session;
import java.lang.reflect.Type;

/**
 * Created by Cobalt <unger1984@gmail.com> on 22.06.16.
 */
public class LoginHandler extends SocketMessageHandler<LoginData> {

    private static final Logger LOG = Logger.getLogger(LoginHandler.class);

    public LoginHandler(MessageHandlerContext context) {
        super(context);
    }

    @Override
    public Response handle(Session session, String message) {
        LoginData data = getMessageData(message);

        Response response = new Response();
        TokenData tokenData = context.persistenceService.authenticate(data.getEmail(),data.getPassword());
        if(tokenData==null){
            response.setSequence_id(getSequence_id(message));
            response.setType("CUSTOMER_ERROR");
            response.setData(new ErrorData(ErrorData.ERR_AUTH,"Customer not found"));
        }else {
            response.setSequence_id(getSequence_id(message));
            response.setType("CUSTOMER_API_TOKEN");
            response.setData(tokenData);
        }
        return response;
    }

    @Override
    protected Type createMessageType() {
        return new TypeToken<SocketMessage<LoginData>>() {
        }.getType();
    }

}
