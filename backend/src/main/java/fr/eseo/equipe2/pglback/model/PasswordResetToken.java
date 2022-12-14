package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {
    private static final int EXPIRATION = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public PasswordResetToken() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, EXPIRATION);

        expiryDate = cal.getTime();
    }

    public PasswordResetToken(String token, User user) {
        this();
        this.token = token;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public PasswordResetToken setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public PasswordResetToken setToken(String token) {
        this.token = token;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PasswordResetToken setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public PasswordResetToken setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}