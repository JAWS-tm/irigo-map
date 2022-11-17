package fr.eseo.equipe2.pglback;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import fr.eseo.equipe2.pglback.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserDaoTests {
    @Autowired
    private UserDao userDao;

    /**
     * We create one user and testing if all is done well with/without frequency and motion
     */
    @Test
    public void testCreateUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("b3"); //encode password
        UserSex sex= UserSex.MALE;
        Date birthday1= new Date("1993/06/20");
        TravelFrequency frequency = TravelFrequency.WEEKLY;
        TravelHabits motion = TravelHabits.BICYCLE;
        User newUser = new User("patrick.dubois@reseau.eseo.fr", password, "Patrick",
                "Dubois", sex, birthday1, motion, frequency);
        userDao.save(newUser);

        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String password2 = passwordEncoder2.encode("B3"); //encode password
        UserSex sex2 = UserSex.FEMALE;
        Date birthday2 = new Date("1954/12/01");
        User newUser2 = new User("michelle.oro@reseau.eseo.fr", password2, "Michelle",
                "Oro", sex2, birthday2);
        userDao.save(newUser2);

    }

}