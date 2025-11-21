package com.jszarski.bookservice.controller;


import com.jszarski.bookservice.service.BookService;
import com.jszarski.common.model.dto.BookAddDTO;
import com.jszarski.common.model.dto.BookDTO;
import com.jszarski.common.model.dto.BookRatingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{name}")
    public List<BookDTO> getBooksByName(@PathVariable("name") String name) {
        log.info("GET /book/{}", name);
        return bookService.getBook(name);
    }

    @PostMapping
    public BookDTO addBook(@RequestBody BookAddDTO bookAddDTO){
        log.info("POST /book");
        return bookService.addBook(bookAddDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
