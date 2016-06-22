package ru.sp.fasten.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cobalt on 22.06.16.
 */
@Entity
@Table(name = "token")
@NamedQueries({
        @NamedQuery(name = TokenBE.FIND_TOKEN_BY_EMAIL, query = "select t from TokenBE t where t.user.email = :email"),
        @NamedQuery(name = TokenBE.FIND_TOKEN_BY_TOKENID, query = "select t from TokenBE t where t.token = :token"),
})
public class TokenBE {

    private static final long serialVersionUID = 6008544547797158618L;

    public static final String FIND_TOKEN_BY_EMAIL = "TokenBE.findByEmail";
    public static final String FIND_TOKEN_BY_TOKENID = "TokenBE.findByToken";

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
}
