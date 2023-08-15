package com.spa.tacocloud.controller;

import com.spa.tacocloud.model.TacoOrder;
import com.spa.tacocloud.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        System.out.println(order);
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
