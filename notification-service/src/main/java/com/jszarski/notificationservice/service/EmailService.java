package com.jszarski.notificationservice.service;


import com.jszarski.common.model.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendNewRecommendation(NotificationEvent event) {
        log.info("Sending recommendation to {}", event.getEmail());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(event.getEmail());
        mailMessage.setSubject("New book recommendation");
        mailMessage.setText("Title: " + event.getRecommendation().getName() + "\n" +
                "Author: " + event.getRecommendation().getAuthor() + "\n" +
                "Genre: " + event.getRecommendation().getGenre());
        javaMailSender.send(mailMessage);
        log.info("Mail sent to {}", event.getEmail());
    }

    public void sendPopularRecommendation(NotificationEvent event) {
        log.info("Sending recommendation to {}", event.getEmail());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(event.getEmail());
        mailMessage.setSubject("Popular book recommendation");
        mailMessage.setText("Title: " + event.getRecommendation().getName() + "\n" +
                "Author: " + event.getRecommendation().getAuthor() + "\n" +
                "Genre: " + event.getRecommendation().getGenre() + "\n " +
                "Rating: " + event.getRecommendation().getRatingAvg());
        javaMailSender.send(mailMessage);
        log.info("Mail sent to {}", event.getEmail());
    }
}
