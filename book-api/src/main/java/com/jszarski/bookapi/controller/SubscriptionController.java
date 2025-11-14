package com.jszarski.bookapi.controller;

import com.jszarski.bookapi.model.dto.SubscriptionDTO;
import com.jszarski.bookapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
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
