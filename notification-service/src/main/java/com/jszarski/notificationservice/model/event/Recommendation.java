package com.jszarski.notificationservice.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private String name;
    private String author;
    private String genre;
    private String ratingAvg;
}
