package com.jszarski.bookservice.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSubscriptionId implements Serializable {
    private UUID bookId;
    private UUID subscriptionId;
}
