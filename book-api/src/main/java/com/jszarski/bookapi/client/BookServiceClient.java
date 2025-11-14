package com.jszarski.bookapi.client;

import com.jszarski.common.model.dto.BookAddDTO;
import com.jszarski.common.model.dto.BookDTO;
import com.jszarski.common.model.dto.BookRatingDTO;
import com.jszarski.common.model.dto.SubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "book-service",
        url = "${book-service.url}"
)
public interface BookServiceClient {

    @GetMapping("/book/{name}")
    BookDTO getBook(@PathVariable("name") String name);

    @PostMapping("/book")
    BookDTO addBook(@RequestBody BookAddDTO bookAddDTO);

    @DeleteMapping("/book/{id}")
    void deleteBook(@PathVariable("id") UUID id);

    @PostMapping("/book/rate")
    void rate(@RequestBody BookRatingDTO bookRatingDTO);

    @PostMapping("/subscription")
    void subscribe(@RequestBody SubscriptionDTO subscriptionDTO);

    @GetMapping("/subscription/{email}")
    List<SubscriptionDTO> getSubscriptions(@PathVariable("email") String email);
}
