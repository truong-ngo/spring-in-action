package com.spa.tacocloud.repository;

import com.spa.tacocloud.model.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
}
