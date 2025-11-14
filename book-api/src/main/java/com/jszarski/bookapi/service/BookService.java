package com.jszarski.bookapi.service;

import com.jszarski.bookapi.client.BookServiceClient;
import com.jszarski.bookapi.model.dto.BookAddDTO;
import com.jszarski.bookapi.model.dto.BookDTO;
import com.jszarski.bookapi.model.dto.BookRatingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {

    private final BookServiceClient bookServiceClient;

    public BookDTO getBook(String name) {
        log.info("Getting book by name {}", name);
        try {
            return bookServiceClient.getBook(name);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
            return null;
        }
    }

    public BookDTO addBook(BookAddDTO bookAddDTO){
        log.info("Adding book {}", bookAddDTO.getName());
        try {
            return bookServiceClient.addBook(bookAddDTO);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
            return null;
        }
    }

    public void deleteBook(UUID id){
        log.info("Deleting book {}", id);
        try {
            bookServiceClient.deleteBook(id);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
        }
    }

    public void rate(BookRatingDTO bookRatingDTO) {
        log.info("Rating book {}", bookRatingDTO.getName());
        try {
            bookServiceClient.rate(bookRatingDTO);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
        }
    }
}
