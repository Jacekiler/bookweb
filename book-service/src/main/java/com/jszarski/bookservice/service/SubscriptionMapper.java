package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.Subscription;
import com.jszarski.common.model.dto.SubscriptionDTO;
import com.jszarski.common.model.dto.SubscriptionType;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public SubscriptionDTO toDto(Subscription subscription) {
        return SubscriptionDTO.builder()
                .id(subscription.getId())
                .email(subscription.getEmail())
                .author(subscription.getAuthor())
                .genre(subscription.getGenre())
                .ratingAvg(subscription.getRatingAvg())
                .type(SubscriptionType.valueOf(subscription.getType()))
                .build();
    }

    public Subscription fromDto(SubscriptionDTO subscriptionDTO){
        return Subscription.builder()
                .email(subscriptionDTO.getEmail())
                .author(subscriptionDTO.getAuthor())
                .genre(subscriptionDTO.getGenre())
                .ratingAvg(subscriptionDTO.getRatingAvg())
                .type(subscriptionDTO.getType().name())
                .build();
    }
}
