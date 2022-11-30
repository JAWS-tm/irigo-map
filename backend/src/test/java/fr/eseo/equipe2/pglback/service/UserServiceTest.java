package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.UserDaoTests;
import fr.eseo.equipe2.pglback.controller.request.ForgotPasswordRequest;
import fr.eseo.equipe2.pglback.controller.request.RegisterRequest;
import fr.eseo.equipe2.pglback.dao.PasswordResetTokenDao;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import fr.eseo.equipe2.pglback.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private UserDao userDao;

    @Test
    void createDefaultUser() {
        userDao.save(new User(
                "jules.dempt@outlook.fr",
                "test",
                "Jean",
                "Test",
                UserSex.MALE,
                new Date()
        ));
    }

    @Test
    void forgotPassword() {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("jules.dempt@outlook.fr");

        String res = restTemplate.postForObject("http://localhost:"+port+"/api/users/forgot-password", forgotPasswordRequest, String.class);
        System.out.println(res);
        assertTrue(res.contains("OK"));

        System.out.println(passwordResetTokenDao.getReferenceById(1));
    }

    @Test
    void validatePasswordToken() {
    }

    @Test
    void updatePassword() {
    }
}