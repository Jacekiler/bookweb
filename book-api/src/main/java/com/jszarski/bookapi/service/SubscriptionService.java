package com.jszarski.bookapi.service;

import com.jszarski.bookapi.client.BookServiceClient;
import com.jszarski.bookapi.model.dto.SubscriptionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
@Log4j2
public class SubscriptionService {

    private final BookServiceClient bookServiceClient;

    public List<SubscriptionDTO> getSubscriptions(String email) {
        log.info("Getting subscriptions for email {}", email);
        try{
            return bookServiceClient.getSubscriptions(email);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
            return emptyList();
        }
    }

    public void subscribe(SubscriptionDTO subscriptionDTO) {
        log.info("Subscribing email {}", subscriptionDTO.getEmail());
        try {
            bookServiceClient.subscribe(subscriptionDTO);
        } catch (Exception e){
            // TODO implement
            log.warn("Unsupported operation. Ex: {}", e.getMessage());
        }
    }
}
