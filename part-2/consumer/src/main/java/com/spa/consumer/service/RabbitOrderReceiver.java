package com.spa.consumer.service;

import com.spa.consumer.model.TacoOrder;
import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderReceiver implements OrderReceiver {
    private final RabbitTemplate rabbit;
    private MessageConverter converter;

    @Override
    public TacoOrder receiveOrder() {
        Message message = rabbit.receive("tacocloud.order.queue");
        return message != null ? (TacoOrder) getConverter().fromMessage(message) : null;
    }

    @Override
    public TacoOrder receiveAndConvertOrder() {
        return (TacoOrder) rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<TacoOrder>() {});
    }

    private MessageConverter getConverter() {
        if (converter == null) {
            converter = rabbit.getMessageConverter();
        }
        return converter;
    }
}
