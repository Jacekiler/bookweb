package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.BookSubscription;
import com.jszarski.bookservice.model.entity.BookSubscriptionId;
import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.bookservice.repository.BookSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationEventProcessor notificationEventProcessor;
    private final BookSubscriptionRepository bookSubscriptionRepository;
    private final SubscriptionsFinder subscriptionsFinder;

    @Async
    public void sendNewNotifications(Book book) {
        List<BookSubscription> newBookSubscriptions = new ArrayList<>();
        subscriptionsFinder.newSubscriptionsToEmit(book)
                .forEach(subscription -> {
                    notificationEventProcessor.sendNew(subscription, book);
                    var bookSubscription = getBookSubscription(subscription, book);
                    newBookSubscriptions.add(bookSubscription);
                });
        log.info("Sent {} new book notifications", newBookSubscriptions.size());
        bookSubscriptionRepository.saveAll(newBookSubscriptions);
    }

    @Async
    public void sendPopularNotifications(Book book) {
        List<BookSubscription> newBookSubscriptions = new ArrayList<>();
        subscriptionsFinder.popularSubscriptionsToEmit(book)
                .forEach(subscription -> {
                    notificationEventProcessor.sendPopular(subscription, book);
                    var bookSubscription = getBookSubscription(subscription, book);
                    newBookSubscriptions.add(bookSubscription);
                });
        log.info("Sent {} popular book notifications", newBookSubscriptions.size());
        bookSubscriptionRepository.saveAll(newBookSubscriptions);
    }

    private BookSubscription getBookSubscription(Subscription subscription, Book book) {
        return new BookSubscription(
                new BookSubscriptionId(book.getId(), subscription.getId()),
                book, // todo book and subscription necessary here?
                subscription,
                LocalDateTime.now()
        );
    }
}
