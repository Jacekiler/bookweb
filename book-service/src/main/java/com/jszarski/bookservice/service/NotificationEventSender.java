package com.jszarski.bookservice.service;

import com.jszarski.common.model.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationEventSender {

    private static final String NEW_RECOMMENDATION_TOPIC = "new-recommendations";
    private static final String POPULAR_RECOMMENDATION_TOPIC = "popular-recommendations";

    private final KafkaTemplate<UUID, NotificationEvent> kafkaTemplate;

    public void sendNewRecommendation(NotificationEvent event) {
        var key = UUID.randomUUID();
        log.info("Sending NotificationEvent to topic \"{}\" with key \"{}\"", NEW_RECOMMENDATION_TOPIC, key);
        kafkaTemplate.send(NEW_RECOMMENDATION_TOPIC, key, event);
        log.info("Event {} sent.", key);
    }

    public void sendPopularRecommendation(NotificationEvent event) {
        var key = UUID.randomUUID();
        log.info("Sending NotificationEvent to topic \"{}\" with key \"{}\"", POPULAR_RECOMMENDATION_TOPIC, key);
        kafkaTemplate.send(POPULAR_RECOMMENDATION_TOPIC, key, event);
        log.info("Event {} sent.", key);
    }
}
