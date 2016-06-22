package ru.sp.fasten.core.data;

import java.util.Date;

/**
 * Created by Cobalt <unger1984@gmail.com> on 22.06.16.
 */
public class TokenData {

    private String api_token;
    private Date api_token_expiration_date;

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public Date getApi_token_expiration_date() {
        return api_token_expiration_date;
    }

    public void setApi_token_expiration_date(Date api_token_expiration_date) {
        this.api_token_expiration_date = api_token_expiration_date;
    }
}
