package com.jszarski.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private UUID id;
    private String name;
    private String author;
    private String genre;
    private int rateCount;
    private Double ratingAvg;
}
