package com.jszarski.bookapi.controller;

import com.jszarski.bookapi.model.dto.BookAddDTO;
import com.jszarski.bookapi.model.dto.BookDTO;
import com.jszarski.bookapi.model.dto.BookRatingDTO;
import com.jszarski.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{name}")
    public BookDTO getBookByName(@PathVariable("name") String name) {
        return bookService.getBook(name);
    }

    @PostMapping
    public BookDTO addBook(@RequestBody BookAddDTO bookAddDTO){
        return bookService.addBook(bookAddDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") UUID id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/rate")
    public void rate(@RequestBody BookRatingDTO bookRatingDTO){
        bookService.rate(bookRatingDTO);
    }
}
