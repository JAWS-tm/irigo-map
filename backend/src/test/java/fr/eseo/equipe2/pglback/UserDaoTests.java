package fr.eseo.equipe2.pglback;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.enumeration.TravelFrequency;
import fr.eseo.equipe2.pglback.enumeration.TravelHabits;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
public class UserDaoTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    /**
     * We create one user and testing if all is done well with/without frequency and motion
     */
    @Test
    public void testCreateUser() throws ParseException {
        DateFormat formater = new SimpleDateFormat("dd-MM-yy");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("b3"); //encode password
        Date birthday1=  formater.parse("10-05-2002");

        User newUser = new User("patrick.dubois@resu.seo.fraa", password, "Patrick",
                "Dubois", UserSex.MALE, birthday1, TravelHabits.BICYCLE, TravelFrequency.WEEKLY);
        userDao.save(newUser);

    }
    //testing delete user
    @Test
    public void testDeleteUser(){
        if(userDao.existsByEmail("patrick.dubois@reseau.eseo.fr")){
            userService.deleteUser("patrick.dubois@reseau.eseo.fr");
        }
        System.out.println("test delete user");
    }

}
