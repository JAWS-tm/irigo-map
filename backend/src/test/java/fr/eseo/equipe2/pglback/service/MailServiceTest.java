package fr.eseo.equipe2.pglback.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "test"))
            .withPerMethodLifecycle(false);


    @Test
    void sendHtmlMessage() throws MessagingException, IOException {
        String to = "jules.dempt@outlook.fr";
        String subject = "Test message";
        String template = "resetPassword.html";
        HashMap<String, Object> content = new HashMap<>();
        content.put("name", "TEST");

        mailService.sendHtmlMessage(to, subject, template, content);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(subject, current.getSubject());
        assertEquals(to, current.getAllRecipients()[0].toString());
//        assertTrue(String.valueOf(current.getContent()).contains("TEST"));
    }
}