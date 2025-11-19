package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationEventBuilderTest {

    private static final UUID ID = UUID.fromString("666f325e-5432-e420-9b2c-116775444abc");
    private static final String NAME = "bookName";
    private static final String AUTHOR = "author";
    private static final String GENRE = "fantasy";
    private static final Double RATING_SUM = 10.0;
    private static final int RATING_COUNT = 2;
    private static final Double RATING_AVG = 5.0;
    private static final String EMAIL = "email";

    private final NotificationEventBuilder builder = new NotificationEventBuilder();

    @Test
    public void build() {
        //given
        Book book = Book.builder()
                .id(ID)
                .name(NAME)
                .author(AUTHOR)
                .genre(GENRE)
                .ratingSum(RATING_SUM)
                .ratingCount(RATING_COUNT)
                .ratingAvg(RATING_AVG)
                .build();

        //when
        var result = builder.build(EMAIL, book);

        //then
        assertAll(
                () -> assertEquals(EMAIL, result.getEmail()),
                () -> assertEquals(NAME, result.getRecommendation().getName()),
                () -> assertEquals(AUTHOR, result.getRecommendation().getAuthor()),
                () -> assertEquals(GENRE, result.getRecommendation().getGenre()),
                () -> assertEquals(RATING_AVG, result.getRecommendation().getRatingAvg())
        );
    }
}