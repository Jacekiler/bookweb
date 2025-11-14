package com.jszarski.bookservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
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

