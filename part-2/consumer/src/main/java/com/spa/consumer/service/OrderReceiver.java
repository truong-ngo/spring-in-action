package com.spa.consumer.service;

import com.spa.consumer.model.TacoOrder;
import jakarta.jms.JMSException;

public interface OrderReceiver {
    TacoOrder receiveOrder() throws JMSException;
    TacoOrder receiveAndConvertOrder();
}
