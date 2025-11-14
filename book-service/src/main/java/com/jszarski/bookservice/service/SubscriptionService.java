package com.jszarski.bookservice.service;


import com.jszarski.bookservice.model.dto.SubscriptionDTO;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    public List<SubscriptionDTO> getSubscriptions(String email) {
        log.info("Getting subscriptions for email {}", email);
        return subscriptionRepository.findAllByEmail(email)
                .stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }

    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) {
        log.info("Adding subscription for email {}", subscriptionDTO.getEmail());
        var subscription = subscriptionMapper.fromDto(subscriptionDTO);
        var saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(saved);
    }
}
