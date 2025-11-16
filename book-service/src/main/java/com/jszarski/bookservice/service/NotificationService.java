package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.BookSubscription;
import com.jszarski.bookservice.model.entity.BookSubscriptionId;
import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.bookservice.repository.BookSubscriptionRepository;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import com.jszarski.common.model.dto.SubscriptionType;
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

    private static final Double DEFAULT_POPULAR_RATING = 7.0;
    private static final int DEFAULT_POPULAR_RATING_COUNTS_MIN = 10;

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationEventBuilder notificationEventBuilder;
    private final NotificationEventProducer notificationEventProducer;
    private final BookSubscriptionRepository bookSubscriptionRepository;

    @Async
    public void checkNew(Book book) {

        List<BookSubscription> newBookSubscriptions = new ArrayList<>();
        subscriptionRepository.findAllByType(SubscriptionType.NEW.name()).stream()
                .filter(subscription -> matchesNewBook(book, subscription))
                .forEach(subscription -> {
                    var event = notificationEventBuilder.build(subscription.getEmail(), book);
                    notificationEventProducer.sendNewRecommendation(event); // todo send batch kafka?
                    var bookSubscription = new BookSubscription(
                            new BookSubscriptionId(book.getId(), subscription.getId()),
                            book, // todo book and subscription necessary here?
                            subscription,
                            LocalDateTime.now()
                    );
                    newBookSubscriptions.add(bookSubscription);
                });
        log.info("Sent {} new book notifications", newBookSubscriptions.size());
        bookSubscriptionRepository.saveAll(newBookSubscriptions);
    }

    private boolean matchesNewBook(Book book, Subscription subscription) {
        return (subscription.getAuthor() == null || subscription.getAuthor().equals(book.getAuthor()))
                && (subscription.getGenre() == null || subscription.getGenre().equals(book.getGenre()));
    }

    @Async
    public void checkPopular(Book book) {
        var sentSubscriptionsIds = bookSubscriptionRepository.findSubscriptionsIdsByBook(book.getId());

        List<BookSubscription> newBookSubscriptions = new ArrayList<>();
        subscriptionRepository.findAllByType(SubscriptionType.TOP_RATED.name()).stream()
                .filter(subscription -> matchesPopularBook(book, subscription))
                .filter(subscription -> !sentSubscriptionsIds.contains(subscription.getId()))
                .forEach(subscription -> {
                    var event = notificationEventBuilder.build(subscription.getEmail(), book);
                    notificationEventProducer.sendPopularRecommendation(event); // todo send batch kafka?
                    var bookSubscription = new BookSubscription(
                            new BookSubscriptionId(book.getId(), subscription.getId()),
                            book, // todo book and subscription necessary here?
                            subscription,
                            LocalDateTime.now()
                    );
                    newBookSubscriptions.add(bookSubscription);
                });
        log.info("Sent {} popular book notifications", newBookSubscriptions.size());
        bookSubscriptionRepository.saveAll(newBookSubscriptions);
    }

    private boolean matchesPopularBook(Book book, Subscription subscription) {
        return book.getRatingCount() >= DEFAULT_POPULAR_RATING_COUNTS_MIN
                && book.getRatingAvg() >= getMinimumRatingAvg(subscription)
                && (subscription.getAuthor() == null || subscription.getAuthor().equals(book.getAuthor()))
                && (subscription.getGenre() == null || subscription.getGenre().equals(book.getGenre()));
    }

    private Double getMinimumRatingAvg(Subscription subscription) {
        return subscription.getRatingAvg() != null ? subscription.getRatingAvg() : DEFAULT_POPULAR_RATING;
    }
}
