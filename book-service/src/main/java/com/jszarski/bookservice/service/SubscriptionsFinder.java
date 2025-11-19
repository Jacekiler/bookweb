package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.bookservice.repository.BookSubscriptionRepository;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import com.jszarski.common.model.dto.SubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscriptionsFinder {

    private static final Double DEFAULT_POPULAR_RATING = 7.0;
    private static final int DEFAULT_POPULAR_RATING_COUNTS_MIN = 10;

    private final SubscriptionRepository subscriptionRepository;
    private final BookSubscriptionRepository bookSubscriptionRepository;

    public List<Subscription> newSubscriptionsToEmit(Book book) {
        return subscriptionRepository.findAllByType(SubscriptionType.NEW.name()).stream()
                .filter(subscription -> matchesNewBook(book, subscription))
                .toList();
    }

    private boolean matchesNewBook(Book book, Subscription subscription) {
        return (subscription.getAuthor() == null || subscription.getAuthor().equals(book.getAuthor()))
                && (subscription.getGenre() == null || subscription.getGenre().equals(book.getGenre()));
    }

    public List<Subscription> popularSubscriptionsToEmit(Book book) {
        var sentSubscriptionsIds = bookSubscriptionRepository.findSubscriptionsIdsByBook(book.getId());
        return subscriptionRepository.findAllByType(SubscriptionType.TOP_RATED.name()).stream()
                .filter(subscription -> matchesPopularBook(book, subscription))
                .filter(subscription -> !sentSubscriptionsIds.contains(subscription.getId()))
                .toList();
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
