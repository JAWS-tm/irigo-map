package fr.eseo.equipe2.pglback.controller.request;

import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserSex sex;
    private Date birthday;


    private TravelHabits travelHabits;
    private TravelFrequency travelFrequency;
}


