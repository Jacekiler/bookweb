package com.jszarski.bookapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRating {
    private String name;
    private String author;
    private Double rating;
}
