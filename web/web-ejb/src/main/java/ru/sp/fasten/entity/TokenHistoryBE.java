package ru.sp.fasten.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cobalt on 22.06.16.
 */
@Entity
@Table(name = "token_history")
public class TokenHistoryBE {

    private static final long serialVersionUID = 6008544547797158619L;

    public TokenHistoryBE(String action, TokenBE token) {
        this.action = action;
        this.setExpireDate(token.getExpireDate());
        this.setLoginDate(token.getLoginDate());
        this.setToken(token.getToken());
        this.setUser(token.getUser());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "logindate")
    private Date loginDate;

    @Column(name = "expiredate")
    private Date expireDate;

    @Column(name = "action")
    private String action;

    @OneToOne
    private UserBE user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public UserBE getUser() {
        return user;
    }

    public void setUser(UserBE user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
