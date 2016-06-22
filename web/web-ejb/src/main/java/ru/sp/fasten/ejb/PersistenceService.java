package ru.sp.fasten.ejb;


import ru.sp.fasten.core.data.TokenData;
import ru.sp.fasten.entity.TokenBE;

/**
 * Created by cobalt on 26.03.15.
 */
public interface PersistenceService {

    TokenData authenticate(String email, String password);
    TokenBE checkAuthenticate(String email);
    TokenBE findToken(String token);
    void expireToken(String token);
    void updateExpireDate(String token);
}
