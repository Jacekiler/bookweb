package com.jszarski.bookservice.service;


import com.jszarski.bookservice.model.dto.SubscriptionDTO;
import com.jszarski.bookservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
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
