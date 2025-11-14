package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.dto.BookAddDTO;
import com.jszarski.bookservice.model.dto.BookDTO;
import com.jszarski.bookservice.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toBookDto(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .ratingAvg(book.getRatingAvg())
                .rateCount(book.getRatingCount())
                .build();
    }

    public Book fromAddDto(BookAddDTO bookAddDTO){
        return Book.builder()
                .name(bookAddDTO.getName())
                .author(bookAddDTO.getAuthor())
                .genre(bookAddDTO.getGenre())
                .build();
    }
}
