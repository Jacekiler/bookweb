package com.jszarski.bookapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private UUID id;
    private String email;
    private String genre;
    private String author;
    private Double ratingAvg;
    private SubscriptionType type;
}

