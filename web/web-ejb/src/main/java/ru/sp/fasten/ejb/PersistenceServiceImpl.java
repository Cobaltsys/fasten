package ru.sp.fasten.ejb;

import org.apache.log4j.Logger;
import ru.sp.fasten.BuildConfig;
import ru.sp.fasten.core.data.TokenData;
import ru.sp.fasten.entity.TokenBE;
import ru.sp.fasten.entity.TokenHistoryBE;
import ru.sp.fasten.entity.UserBE;
import org.joda.time.DateTime;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cobalt on 26.03.15.
 */
@Local(PersistenceService.class)
@Singleton
public class PersistenceServiceImpl implements PersistenceService {

    @PersistenceContext(unitName = BuildConfig.PERSISTENCE_UNIT)
    private EntityManager em;

    private static final Integer ttlMin = 10;

    private static final Logger logger = Logger.getLogger(PersistenceService.class);

    @Override
    public TokenData authenticate(String email, String password) {
        UserBE user = findUser(email, password);
        TokenBE token = null;
        if(user==null){
            return null;
        }
        try {
            token = checkAuthenticate(email);
        } catch (Exception e) {
        }
        if(token != null) {
            deleteToken(token);
            addHistory("RELOGIN_REQUEST", token);
        }
        TokenBE newToken = newToken(user);
        addHistory("TOKEN_CREATED", newToken);
        TokenData tm = new TokenData();
        tm.setApi_token(newToken.getToken());
        tm.setApi_token_expiration_date(newToken.getExpireDate());
        return tm;
    }

    @Override
    public TokenBE checkAuthenticate(String email){
        TypedQuery<TokenBE> query2 = em.createNamedQuery(TokenBE.FIND_TOKEN_BY_EMAIL, TokenBE.class);
        query2.setParameter("email", email);
        List<TokenBE> tokens = query2.getResultList();
        if (tokens == null || tokens.size() == 0) {
            return null;
        }
        for(TokenBE token : tokens) {
            if(!isTokienExpire(token)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public TokenBE findToken(String token) {
        TypedQuery<TokenBE> query = em.createNamedQuery(TokenBE.FIND_TOKEN_BY_TOKENID, TokenBE.class);
        query.setParameter("token", token);
        List<TokenBE> tokens = query.getResultList();
        if (tokens == null || tokens.size() == 0) {
            return null;
        }
        for(TokenBE t : tokens) {
            if(!isTokienExpire(t)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void expireToken(String token) {
        TokenBE t = findToken(token);
        deleteToken(t);
        addHistory("TOKEN_EXPIRE", t);
    }

    @Override
    public void updateExpireDate(String token)   {
        TokenBE t = findToken(token);
        Date now = new Date();
        t.setExpireDate(setTtl(now));
        addHistory("UPDATE_EXPIRE_DATE", t);
    }

    private Date setTtl(Date now) {
        DateTime d = new DateTime(now);
        DateTime expire = d.plusMinutes(ttlMin);
        return expire.toDate();
    }

    private void addHistory(String action, TokenBE token) {
        TokenHistoryBE entity = new TokenHistoryBE(action, token);
        em.persist(entity);

    }

    private TokenBE newToken(UserBE user) {
        Date loginDate = new Date();
        TokenBE newToken = new TokenBE();
        newToken.setUser(user);
        String token = UUID.randomUUID().toString();
        newToken.setToken(token);
        newToken.setExpireDate(setTtl(loginDate));
        newToken.setLoginDate(loginDate);
        em.persist(newToken);
        return newToken;
    }

    private UserBE findUser(String email, String password){
        TypedQuery<UserBE> query = em.createNamedQuery(UserBE.NQ_SELECT_BY_LOGIN_AND_PASSWORD, UserBE.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            List<UserBE> users = query.getResultList();
            if(users.size() == 0)
                return null;
            return users.get(0);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void deleteToken(TokenBE token) {
        em.remove(token);
    }

    private boolean isTokienExpire(TokenBE token) {
        Date sysdate = new Date();
        if(sysdate.after(token.getExpireDate())) {
            deleteToken(token);
            addHistory("TOKEN_EXPIRE_BY_DATE", token);
            return true;
        } else {
            return false;
        }
    }
}
