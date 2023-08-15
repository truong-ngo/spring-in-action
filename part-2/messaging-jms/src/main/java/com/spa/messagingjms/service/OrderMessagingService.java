package com.spa.messagingjms.service;

import com.spa.messagingjms.model.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
    void sendOrderWithDest(TacoOrder order);
    void sendOrderWithDestString(TacoOrder order);
    void convertAndSend(TacoOrder order);
    void sendOrderWithHeader(TacoOrder order);
    void convertAndSendOrderWithHeader(TacoOrder order);
}
