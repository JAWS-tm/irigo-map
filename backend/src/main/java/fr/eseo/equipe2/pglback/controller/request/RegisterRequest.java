package fr.eseo.equipe2.pglback.controller.request;

import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;

import java.util.Date;

//@Getter
//@Setter
//@Accessors(chain = true)
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserSex sex;
    private Date birthday;


    private TravelHabits travelHabits;
    private TravelFrequency travelFrequency;

    public RegisterRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSex getSex() {
        return sex;
    }

    public RegisterRequest setSex(UserSex sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public RegisterRequest setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public TravelHabits getTravelHabits() {
        return travelHabits;
    }

    public RegisterRequest setTravelHabits(TravelHabits travelHabits) {
        this.travelHabits = travelHabits;
        return this;
    }

    public TravelFrequency getTravelFrequency() {
        return travelFrequency;
    }

    public RegisterRequest setTravelFrequency(TravelFrequency travelFrequency) {
        this.travelFrequency = travelFrequency;
        return this;
    }
}


