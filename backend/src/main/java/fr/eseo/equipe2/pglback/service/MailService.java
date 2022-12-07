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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.module.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Value("${irigomap.front_url}")
    private String frontUrl;

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
        context.setVariable("urlBase", frontUrl);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(from, "Irigo Map"));
            helper.setTo(to);
            helper.setSubject(subject);
            String html = templateEngine.process(template, context);
            helper.setText(html, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    /**
     * Send email to user using text string
     * @param to email of user
     * @param subject email subject
     * @param messageStr message to send
     */
    public void sendMessage(String to, String subject, String messageStr)  {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(from, "Irigo Map"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(messageStr);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }


}
