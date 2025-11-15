package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.common.model.dto.SubscriptionDTO;
import com.jszarski.common.model.dto.SubscriptionType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionMapperTest {

    private static final UUID ID = UUID.fromString("1c1cde5c-9aaa-4845-9607-c75e2519cd7e");
    private static final String EMAIL = "email";
    private static final String AUTHOR = "author";
    private static final String GENRE = "fantasy";
    private static final Double RATING_AVG = 7.5;
    private static final SubscriptionType TYPE = SubscriptionType.TOP_RATED;

    private final SubscriptionMapper mapper = new SubscriptionMapper();

    @Test
    public void mapToDto() {
        //given
        Subscription subscription = Subscription.builder()
                .id(ID)
                .email(EMAIL)
                .author(AUTHOR)
                .genre(GENRE)
                .ratingAvg(RATING_AVG)
                .type(TYPE.name())
                .build();

        //when
        var result = mapper.toDto(subscription);

        //then
        assertAll(
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(EMAIL, result.getEmail()),
                () -> assertEquals(AUTHOR, result.getAuthor()),
                () -> assertEquals(GENRE, result.getGenre()),
                () -> assertEquals(RATING_AVG, result.getRatingAvg()),
                () -> assertEquals(TYPE, result.getType())
        );
    }

    @Test
    public void fromDto() {
        //given
        SubscriptionDTO dto = SubscriptionDTO.builder()
                .email(EMAIL)
                .author(AUTHOR)
                .genre(GENRE)
                .ratingAvg(RATING_AVG)
                .type(TYPE)
                .build();

        //when
        var result = mapper.fromDto(dto);

        //then
        assertAll(
                () -> assertNull(result.getId()),
                () -> assertEquals(EMAIL, result.getEmail()),
                () -> assertEquals(AUTHOR, result.getAuthor()),
                () -> assertEquals(GENRE, result.getGenre()),
                () -> assertEquals(RATING_AVG, result.getRatingAvg()),
                () -> assertEquals(TYPE, SubscriptionType.valueOf(result.getType()))
        );
    }

}