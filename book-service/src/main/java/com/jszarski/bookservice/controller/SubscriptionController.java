package com.jszarski.bookservice.controller;

import com.jszarski.bookservice.model.dto.SubscriptionDTO;
import com.jszarski.bookservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{email}")
    List<SubscriptionDTO> getSubscriptions(@PathVariable("email") String email){
        log.info("GET /subscription/{}", email);
        return subscriptionService.getSubscriptions(email);
    }

    @PostMapping
    SubscriptionDTO subscribe(@RequestBody SubscriptionDTO subscriptionDTO){
        log.info("POST /subscription");
        return subscriptionService.subscribe(subscriptionDTO);
    }
}
