package com.spa.rsocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class GreetingController {
    @MessageMapping("greeting")
    public Mono<String> handlerGreeting(Mono<String> greeting) {
        return greeting
                .doOnNext(g -> log.info("Received a greeting: {}", g))
                .map(g -> "Hello back to you!");
    }

    @MessageMapping("greeting/{name}")
    public Mono<String> handleGreetingWithPath(@DestinationVariable("name") String name, Mono<String> greeting) {
        return greeting
                .doOnNext(g -> log.info("Received a greeting from {} : {}", name, g))
                .map(g -> "Hello to you, too, " + name);
    }
}
