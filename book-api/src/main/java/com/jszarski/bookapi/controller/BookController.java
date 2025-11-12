package com.jszarski.bookapi.controller;

import com.jszarski.bookapi.model.dto.Book;
import com.jszarski.bookapi.model.dto.BookRating;
import com.jszarski.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{name}")
    public Book getBookByName(@PathVariable("name") String name) {
        return bookService.getBook(name);
    }

    @PostMapping
    public void rate(@RequestBody BookRating bookRating){
        bookService.rate(bookRating);
    }
}
