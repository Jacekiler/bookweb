package com.jszarski.bookapi.controller;

import com.jszarski.bookapi.model.dto.BookAddDTO;
import com.jszarski.bookapi.model.dto.BookDTO;
import com.jszarski.bookapi.model.dto.BookRatingDTO;
import com.jszarski.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{name}")
    public BookDTO getBookByName(@PathVariable("name") String name) {
        log.info("GET /book/{}", name);
        return bookService.getBook(name);
    }

    @PostMapping
    public BookDTO addBook(@RequestBody BookAddDTO bookAddDTO){
        log.info("POST /book");
        return bookService.addBook(bookAddDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") UUID id) {
        log.info("DELETE /book/{}", id);
        bookService.deleteBook(id);
    }

    @PostMapping("/rate")
    public void rate(@RequestBody BookRatingDTO bookRatingDTO){
        log.info("POST /book/rate");
        bookService.rate(bookRatingDTO);
    }
}
