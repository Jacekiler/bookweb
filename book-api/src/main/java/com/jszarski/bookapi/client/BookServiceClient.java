package com.jszarski.bookapi.client;

import com.jszarski.bookapi.model.dto.Book;
import com.jszarski.bookapi.model.dto.BookRating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "book-service",
        url = "${book-service.url}"
)
public interface BookServiceClient {

    @GetMapping("/book/{name}")
    Book getBook(@PathVariable("name") String name);

    @PostMapping("/book")
    void rate(@RequestBody BookRating bookRating);
}
