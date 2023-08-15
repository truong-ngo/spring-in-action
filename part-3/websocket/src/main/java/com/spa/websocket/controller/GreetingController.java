package com.spa.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@CrossOrigin("*")
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
