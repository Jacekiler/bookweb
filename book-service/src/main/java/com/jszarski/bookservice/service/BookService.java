package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.dto.BookAddDTO;
import com.jszarski.bookservice.model.dto.BookDTO;
import com.jszarski.bookservice.model.dto.BookRatingDTO;
import com.jszarski.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    public Optional<BookDTO> getBook(String name) {
        log.info("Looking for book {} in db", name);
        return bookRepository.findByName(name)
                .map(bookMapper::toBookDto);
    }

    public BookDTO addBook(BookAddDTO bookAddDTO){
        log.info("Adding book {} to db", bookAddDTO.getName());
        var book = bookMapper.fromAddDto(bookAddDTO);
        var saved = bookRepository.save(book);
        return bookMapper.toBookDto(saved);
    }

    public void deleteBook(UUID id){
        log.info("Deliting book {}", id);
        bookRepository.deleteById(id);
    }

    @Transactional
    public void rate(BookRatingDTO bookRatingDTO) {
        log.info("Updating rating for book {}", bookRatingDTO.getName());
        bookRepository.findByName(bookRatingDTO.getName())
                .ifPresent(book -> {
                    var ratingSum = book.getRatingSum() == null ? 0.0 : book.getRatingSum();
                    var newRatingSum = ratingSum + bookRatingDTO.getRating();
                    var newRatingCount = book.getRatingCount() + 1;
                    var newAvgRating = newRatingSum / newRatingCount;
                    book.setRatingSum(newRatingSum);
                    book.setRatingCount(newRatingCount);
                    book.setRatingAvg(newAvgRating);
                });
    }
}
