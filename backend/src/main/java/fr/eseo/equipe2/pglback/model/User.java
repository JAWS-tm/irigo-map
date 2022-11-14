package fr.eseo.equipe2.pglback.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users") //name of the table on database
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="login",length = 30, nullable = false, unique = true)
    private String login; // e-mail
    @Column(name="password",length = 60, nullable = false)
    private String password;
    @Column(name="name",length = 30, nullable = false)
    private String name;
    @Column(name="lastName",length = 30, nullable = false)
    private String lastName;
    @Column(name="sex",length = 30 ,nullable = false)
    private String sex;
    @Column(name="dateBirth",length = 30 ,nullable = false)
    private String dateBirth;
    @Column(name="motion",length = 40 ,nullable = true)
    private String motion;
    @Column(name="frequency",length = 30 ,nullable = true)
    private String frequency;

    public User() {}

    /**
     * when we want new user we take all parameters
     * @param login     e-mail
     * @param password  password
     * @param name      first name
     * @param lastName  last name
     * @param sex       (H,F,other,no precision)
     * @param dateBirth (AAAA-MM-DD)
     * @param motion    type of motion
     * @param frequency how many times
     */
    public User(String login, String password, String name, String lastName, String sex, String dateBirth, String motion, String frequency) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.motion = motion;
        this.frequency = frequency;
    }

    /**
     * when we want new user without motion and frequency
     * @param login email of user
     * @param password  password of user
     * @param name      first name
     * @param lastName  last name
     * @param sex       sex (H,F,other,no precision)
     * @param dateBirth date of birth (AAAA-MM-DD)
     */
    public User(String login, String password, String name, String lastName, String sex, String dateBirth) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.dateBirth = dateBirth;
    }
    // getters
    public String getLogin() {return login;}
    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getLastName() {return lastName;}
    public String getSex() {return sex;}
    public String getDateBirth() {return dateBirth;}
    public String getMotion() {return motion;}
    public String getFrequency() {return frequency;}

    // setters
    public void setLogin(String login) {this.login = login;}
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {this.name = name;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setSex(String sex) {this.sex = sex;}
    public void setDateBirth(String dateBirth) {this.dateBirth = dateBirth;}
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
