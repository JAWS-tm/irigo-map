package fr.eseo.equipe2.pglback.controller.request;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private UserSex sex;
    @NotNull
    private Date birthday;

    private TravelHabits travelHabits;
    private TravelFrequency travelFrequency;
}


