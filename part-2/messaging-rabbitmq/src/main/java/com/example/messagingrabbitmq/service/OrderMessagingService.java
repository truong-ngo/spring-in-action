package com.example.messagingrabbitmq.service;

import com.example.messagingrabbitmq.model.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
    void convertAndSendOrder(TacoOrder order);
}
