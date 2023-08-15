package com.spa.rsocket.controller;

import com.spa.rsocket.model.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class AlertController {
    @MessageMapping("alert")
    public Mono<Void> setAlert(Mono<Alert> alert) {
        return alert
                .doOnNext(a -> log.info("{} alert ordered by {} at {}", a.getLevel(), a.getOrderedBy(), a.getOrderedAt()))
                .thenEmpty(Mono.empty());
    }
}
