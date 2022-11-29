package fr.eseo.equipe2.pglback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.lang.module.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private  SpringTemplateEngine templateEngine;

    /**
     * Send email to user using template (in /resources/templates/mails/)
     * @param to email of user
     * @param subject email subject
     * @param template name of the template (ex : resetPassword.html)
     * @param templateData map of each variable used in the template file
     */
    public void sendHtmlMessage(String to, String subject, String template, Map<String, Object> templateData)  {
        MimeMessage message = javaMailSender.createMimeMessage();

        Context context = new Context();
        context.setVariables(templateData);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            String html = templateEngine.process(template, context);
            helper.setText(html, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }


}
