package com.spa.reactiver2dbc.repository;

import com.spa.reactiver2dbc.model.TacoOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {

}
