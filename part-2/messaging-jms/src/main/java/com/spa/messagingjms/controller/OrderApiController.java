package com.spa.messagingjms.controller;

import com.spa.messagingjms.model.TacoOrder;
import com.spa.messagingjms.service.OrderMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderMessagingService messagingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        messagingService.sendOrder(order);
        return order;
    }
}
