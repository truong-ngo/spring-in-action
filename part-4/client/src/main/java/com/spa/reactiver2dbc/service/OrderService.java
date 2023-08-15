package com.spa.reactiver2dbc.service;

import com.spa.reactiver2dbc.model.Taco;
import com.spa.reactiver2dbc.model.TacoOrder;
import com.spa.reactiver2dbc.repository.OrderRepository;
import com.spa.reactiver2dbc.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TacoRepository tacoRepository;

    public Mono<TacoOrder> save(TacoOrder order) {
        return Mono.just(order).flatMap(o -> {
            List<Taco> tacos = o.getTacos();
            o.setTacos(new ArrayList<>());
            return tacoRepository.saveAll(tacos).map(taco -> {
                o.addTaco(taco);
                o.setStringTacos();
                return o;
            }).last();
        }).flatMap(orderRepository::save);
    }

    public Mono<TacoOrder> findById(Long id) {
        return orderRepository.findById(id).flatMap(order -> tacoRepository
                .findAllById(order.getTacoIdList())
                .map(taco -> {
                    order.addTaco(taco);
                    return order;
                }).last());
    }
}
