package com.spa.consumer.service;

import com.spa.consumer.model.TacoOrder;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsOrderReceiver implements OrderReceiver {
    private final JmsTemplate jms;
    private final MessageConverter converter;


    @Override
    public TacoOrder receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");
        assert message != null;
        return (TacoOrder) converter.fromMessage(message);
    }

    @Override
    public TacoOrder receiveAndConvertOrder() {
        return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
