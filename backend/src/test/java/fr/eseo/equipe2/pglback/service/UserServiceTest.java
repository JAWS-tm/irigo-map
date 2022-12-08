package fr.eseo.equipe2.pglback.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import fr.eseo.equipe2.pglback.dao.PasswordResetTokenDao;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import fr.eseo.equipe2.pglback.model.PasswordResetToken;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.payload.request.ForgotPasswordRequest;
import fr.eseo.equipe2.pglback.payload.request.PasswordResetRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private UserDao userDao;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "test"))
            .withPerMethodLifecycle(false);

    @Test
    @Order(1)
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
    @Order(2)
    void forgotPassword() throws MessagingException, IOException {
        String email = "jules.dempt@outlook.fr";
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);

        String res = restTemplate.postForObject("http://localhost:"+port+"/api/users/forgot-password", forgotPasswordRequest, String.class);

        assertTrue(res.contains("OK"));

        // check mails
        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        MimeMessage message = receivedMessages[0];

        assertEquals("Réinitialiser votre mot de passe", message.getSubject());
        assertEquals(email, message.getAllRecipients()[0].toString());
    }

    @Test
    @Order(3)
    void validatePasswordToken() {
        PasswordResetToken passwordResetToken = passwordResetTokenDao.getReferenceById(1);

        assertNotNull(passwordResetToken);

        String res = restTemplate.getForObject("http://localhost:"+port+"/api/users/validate-password-token/"+passwordResetToken.getToken(), String.class);
        System.out.println(res);
        assertTrue(res.contains("OK"));
    }

    @Test
    @Order(4)
    void updatePassword() {
        PasswordResetToken passwordResetToken = passwordResetTokenDao.getReferenceById(1);
        System.out.println(passwordResetToken.getToken());
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setToken(passwordResetToken.getToken())
                .setPassword("newPass");
        String res = restTemplate.postForObject("http://localhost:" + port + "/api/users/reset-password/", passwordResetRequest, String.class);
        System.out.println(res);
        assertTrue(res.contains("OK"));
    }

    @Test
    void validateUnknownPasswordToken() {
        String res = restTemplate.getForObject("http://localhost:"+port+"/api/users/validate-password-token/afbafuabf", String.class);

        assertTrue(res.contains("BAD_REQUEST"));
    }


    @Test
    void validateOutdatedPasswordToken() {
        User user = userDao.save(new User(
                "test@test.fr",
                "test",
                "Jean",
                "Test",
                UserSex.MALE,
                new Date()
        ));

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken("abc");
        passwordResetToken.setExpiryDate(new Date());
        passwordResetToken.setUser(user);

        passwordResetTokenDao.save(passwordResetToken);

        String res = restTemplate.getForObject("http://localhost:" + port + "/api/users/validate-password-token/abc", String.class);

        assertTrue(res.contains("BAD_REQUEST"));
    }


}