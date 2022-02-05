package ru.platform.learning.mainservice.service;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Environment env;

    /*Метод для отправки почты
    String subject - Название сообщения
    String body - Тело сообщения
     */

    public void sendMail(String subject, String body, String email){
        mailSender.send(constructEmail(subject, body, email));
    }

    private SimpleMailMessage constructEmail(String subject, String body, String email) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setTo(email);
        mailMessage.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return mailMessage;
    }
}
