package com.jszarski.notificationservice.listener;

import com.jszarski.common.model.event.NotificationEvent;
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

    private static final String NEW_RECOMMENDATION_TOPIC = "new-recommendations";
    private static final String POPULAR_RECOMMENDATION_TOPIC = "popular-recommendations";

    private final EmailService emailService;

    @KafkaListener(topics = NEW_RECOMMENDATION_TOPIC,
            groupId = "${spring.kafka.group-id}",
            containerFactory = "notificationEventConcurrentKafkaListenerContainerFactory")
    public void listenerNewRecommendation(@Header(KafkaHeaders.RECEIVED_KEY) String key, NotificationEvent event){
        log.info("Received event with key: {}", key);
        emailService.sendNewRecommendation(event);
    }

    @KafkaListener(topics = POPULAR_RECOMMENDATION_TOPIC,
            groupId = "${spring.kafka.group-id}",
            containerFactory = "notificationEventConcurrentKafkaListenerContainerFactory")
    public void listenerPopularRecommendation(@Header(KafkaHeaders.RECEIVED_KEY) String key, NotificationEvent event){
        log.info("Received event with key: {}", key);
        emailService.sendPopularRecommendation(event);
    }
}
