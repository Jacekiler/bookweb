package com.jszarski.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRatingDTO {
    private String name;
    private String author;
    private Double rating;
}
