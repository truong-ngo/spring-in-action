package com.spa.reactiver2dbc.repository;

import com.spa.reactiver2dbc.model.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
