package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.bookservice.repository.BookSubscriptionRepository;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import com.jszarski.common.model.dto.SubscriptionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionsFinderTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private BookSubscriptionRepository bookSubscriptionRepository;

    @InjectMocks
    private SubscriptionsFinder subscriptionsFinder;

    @Test
    public void newEvents() {
        //given
        var book = Book.builder().author("author1").genre("fantasy").build();

        // all with default rating
        var sub1 = new Subscription(UUID.randomUUID(),"email1",null,null, null, "TOP_RATED");
        // fantasy with default rating
        var sub2 = new Subscription(UUID.randomUUID(),"email2",null,"fantasy", null, "TOP_RATED");
        // wrong genre
        var sub3 = new Subscription(UUID.randomUUID(),"email3",null,"otherGenre", null, "TOP_RATED");
        // author1 with default rating
        var sub4 = new Subscription(UUID.randomUUID(),"email4","author1",null, null, "TOP_RATED");
        // wrong author
        var sub5 = new Subscription(UUID.randomUUID(),"email5","otherAuthor",null, null, "TOP_RATED");
        // author1 and fantasy with default rating
        var sub6 = new Subscription(UUID.randomUUID(),"email6","author1","fantasy", null, "TOP_RATED");
        // wrong author
        var sub7 = new Subscription(UUID.randomUUID(),"email7","otherAuthor","fantasy", null, "TOP_RATED");

        when(subscriptionRepository.findAllByType(SubscriptionType.NEW.name())).thenReturn(List.of(sub1, sub2, sub3, sub4, sub5, sub6, sub7));

        //when
        var result = subscriptionsFinder.newSubscriptionsToEmit(book);

        //then
        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals(sub1, result.get(0)),
                () -> assertEquals(sub2, result.get(1)),
                () -> assertEquals(sub4, result.get(2)),
                () -> assertEquals(sub6, result.get(3))
        );
    }

    @Test
    public void popularEvents() {
        //given
        var bookId = UUID.randomUUID();
        var book = Book.builder().id(bookId).author("author1").genre("fantasy").ratingAvg(7.5).ratingCount(11).build();
        var presentBookSubscriptionId1 = UUID.randomUUID();
        var presentBookSubscriptionId2 = UUID.randomUUID();

        // all with default rating
        var validSub1 = new Subscription(UUID.randomUUID(),"email1",null,null, null, "TOP_RATED");
        // fantasy with default rating
        var validSub2 = new Subscription(UUID.randomUUID(),"email2",null,"fantasy", null, "TOP_RATED");
        // wrong genre
        var invalidSub3 = new Subscription(UUID.randomUUID(),"email3",null,"otherGenre", null, "TOP_RATED");
        // author1 with default rating
        var validSub4 = new Subscription(UUID.randomUUID(),"email4","author1",null, null, "TOP_RATED");
        // wrong author
        var invalidSub5 = new Subscription(UUID.randomUUID(),"email5","otherAuthor",null, null, "TOP_RATED");
        // author1 and fantasy with default rating
        var validSub6 = new Subscription(UUID.randomUUID(),"email6","author1","fantasy", null, "TOP_RATED");
        // wrong author
        var invalidSub7 = new Subscription(UUID.randomUUID(),"email7","otherAuthor","fantasy", null, "TOP_RATED");
        // valid subscription but already sent to this email
        var invalidSub8 = new Subscription(presentBookSubscriptionId1,"email8",null,null, null, "TOP_RATED");
        // too high rating expected
        var invalidSub9 = new Subscription(UUID.randomUUID(),"email9",null,null, 8.0, "TOP_RATED");
        // valid subscription but already sent to this email
        var invalidSub10 = new Subscription(presentBookSubscriptionId2,"email10","author1","fantasy", null, "TOP_RATED");
        // all with valid minimum rating
        var validSub11 = new Subscription(UUID.randomUUID(),"email1",null,null, 6.0, "TOP_RATED");

        when(subscriptionRepository.findAllByType(SubscriptionType.TOP_RATED.name())).thenReturn(List.of(validSub1, validSub2, invalidSub3, validSub4, invalidSub5, validSub6, invalidSub7, invalidSub8, invalidSub9, invalidSub10, validSub11));
        when(bookSubscriptionRepository.findSubscriptionsIdsByBook(bookId)).thenReturn(Set.of(presentBookSubscriptionId1, presentBookSubscriptionId2));

        //when
        var result = subscriptionsFinder.popularSubscriptionsToEmit(book);

        //then
        assertAll(
                () -> assertEquals(5, result.size()),
                () -> assertEquals(validSub1, result.get(0)),
                () -> assertEquals(validSub2, result.get(1)),
                () -> assertEquals(validSub4, result.get(2)),
                () -> assertEquals(validSub6, result.get(3)),
                () -> assertEquals(validSub11, result.get(4))
        );
    }
}