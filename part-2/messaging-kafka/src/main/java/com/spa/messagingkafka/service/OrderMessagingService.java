package com.spa.messagingkafka.service;

import com.spa.messagingkafka.model.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
