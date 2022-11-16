package fr.eseo.equipe2.pglback.model;

import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @Column(nullable = false)
    private Date birthday;

    @Column
    @Enumerated(EnumType.STRING)
    private TravelHabits travelHabits;

    @Column
    @Enumerated(EnumType.STRING)
    private TravelFrequency travelFrequency;

    public User() {}

    /**
     * when we want new user we take all parameters
     * @param email     e-mail
     * @param password  password
     * @param firstName      first name
     * @param lastName  last name
     * @param sex       (H,F,other,no precision)
     * @param birthday (AAAA-MM-DD)
     */
    public User(String email, String password, String firstName, String lastName, UserSex sex, Date birthday) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthday = birthday;
    }

    /**
     * when we want new user we take all parameters
     * @param email     e-mail
     * @param password  password
     * @param firstName      first name
     * @param lastName  last name
     * @param sex       (H,F,other,no precision)
     * @param birthday (AAAA-MM-DD)
     * @param travelHabits habits of travel
     * @param travelFrequency frequency of travel
     */
    public User(String login, String password, String firstName, String lastName, UserSex sex, Date birthday, TravelHabits travelHabits, TravelFrequency travelFrequency) {
        this.email = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthday = birthday;
        this.travelHabits = travelHabits;
        this.travelFrequency = travelFrequency;
    }


    // Spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getUsername() { return this.email; }
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
