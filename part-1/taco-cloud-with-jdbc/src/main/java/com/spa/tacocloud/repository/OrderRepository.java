package com.spa.tacocloud.repository;

import com.spa.tacocloud.model.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
