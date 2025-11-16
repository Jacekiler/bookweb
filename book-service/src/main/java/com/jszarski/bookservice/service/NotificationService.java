package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import com.jszarski.common.model.dto.SubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private static final Double DEFAULT_POPULAR_RATING = 7.0;
    private static final int DEFAULT_POPULAR_RATING_COUNTS_MIN = 10;

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationEventBuilder notificationEventBuilder;
    private final NotificationEventProducer notificationEventProducer;

    @Async
    public void checkNew(Book book) {
        //todo save and check if this notification was already sent to this user
        var subscriptionsToApply = subscriptionRepository.findAllByType(SubscriptionType.NEW.name())
                .stream()
                .filter(subscription -> matchesNewBook(book, subscription))
                .toList();
        subscriptionsToApply.stream()
                .map(subscription -> notificationEventBuilder.build(subscription.getEmail(), book))
                .forEach(notificationEventProducer::sendNewRecommendation);

    }

    private boolean matchesNewBook(Book book, Subscription subscription) {
        return (subscription.getAuthor() == null || subscription.getAuthor().equals(book.getAuthor()))
                && (subscription.getGenre() == null || subscription.getGenre().equals(book.getGenre()));
    }

    @Async
    public void checkPopular(Book book) {
        //todo save and check if this notification was already sent to this user
        var subscriptionsToApply = subscriptionRepository.findAllByType(SubscriptionType.TOP_RATED.name())
                .stream()
                .filter(subscription -> matchesPopularBook(book, subscription))
                .toList();
        subscriptionsToApply.stream()
                .map(subscription -> notificationEventBuilder.build(subscription.getEmail(), book))
                .forEach(notificationEventProducer::sendPopularRecommendation);
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
