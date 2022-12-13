package fr.eseo.equipe2.pglback.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.eseo.equipe2.pglback.enumeration.Role;
import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;

import java.util.Date;

//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Data Transfer Object used for transfer data between services & controllers
 */
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserSex sex;
    private Date birthday;

    private TravelHabits travelHabits;
    private TravelFrequency travelFrequency;

    private Role role;


    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSex getSex() {
        return sex;
    }

    public UserDto setSex(UserSex sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public UserDto setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public TravelHabits getTravelHabits() {
        return travelHabits;
    }

    public UserDto setTravelHabits(TravelHabits travelHabits) {
        this.travelHabits = travelHabits;
        return this;
    }

    public TravelFrequency getTravelFrequency() {
        return travelFrequency;
    }

    public UserDto setTravelFrequency(TravelFrequency travelFrequency) {
        this.travelFrequency = travelFrequency;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserDto setRole(Role role) {
        this.role = role;
        return this;
    }
}
