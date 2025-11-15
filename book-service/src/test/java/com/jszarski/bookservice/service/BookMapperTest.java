package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.common.model.dto.BookAddDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private static final UUID ID = UUID.fromString("955f325d-9312-4e20-9b2c-676775444de3");
    private static final String NAME = "bookName";
    private static final String AUTHOR = "author";
    private static final String GENRE = "fantasy";
    private static final Double RATING_SUM = 11.0;
    private static final int RATING_COUNT = 2;
    private static final Double RATING_AVG = 5.5;

    private final BookMapper mapper = new BookMapper();

    @Test
    public void mapToDto() {
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
        var result = mapper.toBookDto(book);

        //then
        assertAll(
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(NAME, result.getName()),
                () -> assertEquals(AUTHOR, result.getAuthor()),
                () -> assertEquals(GENRE, result.getGenre()),
                () -> assertEquals(RATING_COUNT, result.getRateCount()),
                () -> assertEquals(RATING_AVG, result.getRatingAvg())
        );
    }

    @Test
    public void fromAddDto() {
        //given
        BookAddDTO addDTO = new BookAddDTO(NAME, AUTHOR, GENRE);

        //when
        var result = mapper.fromAddDto(addDTO);

        //then
        assertAll(
                () -> assertEquals(NAME, result.getName()),
                () -> assertEquals(AUTHOR, result.getAuthor()),
                () -> assertEquals(GENRE, result.getGenre()),
                () -> assertNull(result.getId()),
                () -> assertNull(result.getRatingSum()),
                () -> assertNull(result.getRatingAvg()),
                () -> assertEquals(0, result.getRatingCount())
        );
    }
}