package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationEventProcessor {

    private final NotificationEventBuilder notificationEventBuilder;
    private final NotificationEventSender notificationEventSender;

    public void sendNew(Subscription subscription, Book book) {
        var event = notificationEventBuilder.build(subscription.getEmail(), book);
        notificationEventSender.sendNewRecommendation(event); // todo send batch kafka?
    }

    public void sendPopular(Subscription subscription, Book book) {
        var event = notificationEventBuilder.build(subscription.getEmail(), book);
        notificationEventSender.sendPopularRecommendation(event); // todo send batch kafka?
    }
}
