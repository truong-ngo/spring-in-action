package com.spa.reactiver2dbc.controller;

import com.spa.reactiver2dbc.model.TacoOrder;
import com.spa.reactiver2dbc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> createOrder(@RequestBody TacoOrder order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TacoOrder> findById(@PathVariable Long id) {
        return orderService.findById(id);
    }
}
