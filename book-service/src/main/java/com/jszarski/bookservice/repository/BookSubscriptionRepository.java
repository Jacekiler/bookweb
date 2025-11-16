package com.jszarski.bookservice.repository;

import com.jszarski.bookservice.model.entity.Book;
import com.jszarski.bookservice.model.entity.BookSubscription;
import com.jszarski.bookservice.model.entity.BookSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookSubscriptionRepository extends JpaRepository<BookSubscription, BookSubscriptionId> {

    List<BookSubscription> findAllByBook(Book book);

    @Query("SELECT bs.subscription.id FROM BookSubscription bs WHERE bs.id.bookId=:bookId")
    Set<UUID> findSubscriptionsIdsByBook(@Param("bookId") UUID bookId);
}
