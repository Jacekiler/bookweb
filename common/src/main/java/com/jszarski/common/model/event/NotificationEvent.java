package com.jszarski.common.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String email;
    private List<Recommendation> recommendations;
}
