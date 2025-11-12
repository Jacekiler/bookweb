package com.jszarski.bookapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private String name;
    private String author;
    private Double rating;
    private Integer rateCount;
}
