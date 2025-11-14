package com.jszarski.notificationservice.service;


import com.jszarski.notificationservice.model.event.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    public void send(NotificationEvent event) {
        log.info("Sending {} recommendations to {}", event.getRecommendations().size(), event.getEmail());
        // todo
    }
}
