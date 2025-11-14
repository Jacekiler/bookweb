package com.jszarski.notificationservice.listener;

import com.jszarski.notificationservice.model.event.NotificationEvent;
import com.jszarski.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = "notifications", groupId = "${spring.kafka.group-id}")
    public void listener(@Header(KafkaHeaders.RECEIVED_KEY) String key, NotificationEvent event){
        log.info("Received event with key: {}", key);
        emailService.send(event);
    }
}
