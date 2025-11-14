package com.jszarski.bookapi.controller;

import com.jszarski.bookapi.model.dto.SubscriptionDTO;
import com.jszarski.bookapi.service.SubscriptionService;
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
    void subscribe(@RequestBody SubscriptionDTO subscriptionDTO){
        //todo add validation for dto
        log.info("POST /subscription");
        subscriptionService.subscribe(subscriptionDTO);
    }
}
