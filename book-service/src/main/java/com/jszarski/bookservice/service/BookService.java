package com.jszarski.bookservice.service;

import com.jszarski.bookservice.exception.BookAlreadyExistsException;
import com.jszarski.bookservice.exception.BookNotFoundException;
import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.repository.BookRepository;
import com.jszarski.common.model.dto.BookAddDTO;
import com.jszarski.common.model.dto.BookDTO;
import com.jszarski.common.model.dto.BookRatingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<BookDTO> getBook(String name) {
        log.info("Looking for book {} in db", name);
        return bookRepository.findByName(name)
                .stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    public BookDTO addBook(BookAddDTO bookAddDTO){
        log.info("Adding book {} to db", bookAddDTO.getName());
        if (bookRepository.existsByNameAndAuthor(bookAddDTO.getName(), bookAddDTO.getAuthor())){
            throw new BookAlreadyExistsException(String.format("Book %s of author %s already exists", bookAddDTO.getName(), bookAddDTO.getAuthor()));
        }
        var book = bookMapper.fromAddDto(bookAddDTO);
        var saved = bookRepository.save(book);
        notificationService.checkNew(saved);
        return bookMapper.toBookDto(saved);
    }

    public void deleteBook(UUID id){
        log.info("Deleting book {}", id);
        if(!bookRepository.existsById(id)){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public void rate(BookRatingDTO bookRatingDTO) {
        log.info("Updating rating for book {}", bookRatingDTO.getName());
        var book = bookRepository.findByNameAndAuthor(bookRatingDTO.getName(), bookRatingDTO.getAuthor())
                .orElseThrow(() -> new BookNotFoundException(String.format("Book %s of author %s does not exist", bookRatingDTO.getName(), bookRatingDTO.getAuthor())));
        updateRating(bookRatingDTO, book);
        notificationService.checkPopular(book);
    }

    private void updateRating(BookRatingDTO bookRatingDTO, Book book) {
        var ratingSum = book.getRatingSum() == null ? 0.0 : book.getRatingSum();
        var newRatingSum = ratingSum + bookRatingDTO.getRating();
        var newRatingCount = book.getRatingCount() + 1;
        var newAvgRating = newRatingSum / newRatingCount;
        book.setRatingSum(newRatingSum);
        book.setRatingCount(newRatingCount);
        book.setRatingAvg(newAvgRating);
    }
}
