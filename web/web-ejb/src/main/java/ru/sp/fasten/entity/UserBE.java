package ru.sp.fasten.entity;

import javax.persistence.*;

/**
 * Created by cobalt on 22.06.16.
 */
@Entity
@Table(name = "customer")
@NamedQueries({
        @NamedQuery(name = UserBE.NQ_SELECT_BY_LOGIN_AND_PASSWORD, query = "SELECT u FROM UserBE u WHERE u.email = :email AND u.password = :password"),
        @NamedQuery(name = UserBE.NQ_SELECT_BY_EMAIL, query = "SELECT u FROM UserBE u WHERE u.email = :email"),
})
public class UserBE {

    private static final long serialVersionUID = 6008544547797158617L;

    public static final String NQ_SELECT_BY_LOGIN_AND_PASSWORD = "UserBE.getByEmailAndPassword";
    public static final String NQ_SELECT_BY_EMAIL = "UserBE.getByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
