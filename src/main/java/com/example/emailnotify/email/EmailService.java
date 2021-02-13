package com.example.emailnotify.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender jms;

    @Override
    @Async
    public void send(String to, String email) {
        // TODO Auto-generated method stub

        try {

            MimeMessage mimeMessage = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("confirm your email");
            helper.setFrom("anumshaikh23@gmail.com");
            jms.send(mimeMessage);
            
        } catch (MessagingException e) {
            //TODO: handle exception
            logger.error("failed to send mail", e);
            throw new IllegalStateException("failed to send mail");
        }
    }
    
}
