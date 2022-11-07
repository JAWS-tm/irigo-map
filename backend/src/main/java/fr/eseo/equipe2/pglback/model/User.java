package fr.eseo.equipe2.pglback.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(length = 30, nullable = false)
    private String login; // e-mail
    @Column(length = 60, nullable = false)
    private String password;
    @Id
    @Column(length = 30, nullable = false)
    private String name;
    @Id
    @Column(length = 30, nullable = false)
    private String lastName;
    @Id
    @Column(length = 30 ,nullable = false)
    private String sex;
    @Id
    @Column(length = 30 ,nullable = false)
    private Date dateBirth;
    @Column(length = 40 ,nullable = true)
    private String motion;
    @Column(length = 30 ,nullable = true)
    private String frequency;

    public User() {}

    public User(String login, String password, String name, String lastName, String sex, Date dateBirth, String motion, String frequency) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.motion = motion;
        this.frequency = frequency;
    }
    // getters
    public String getLogin() {
        return login;
    }
    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getLastName() {return lastName;}
    public String getSex() {return sex;}
    public Date getDateBirth() {return dateBirth;}
    public String getMotion() {return motion;}
    public String getFrequency() {return frequency;}

    // setters
    public void setLogin(String login) {this.login = login;}
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {this.name = name;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setSex(String sex) {this.sex = sex;}
    public void setDateBirth(Date dateBirth) {this.dateBirth = dateBirth;}
    public void setMotion(String motion) {this.motion = motion;}
    public void setFrequency(String frequency) {this.frequency = frequency;}

    //problems with security Spring
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getUsername() {
        return this.login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}