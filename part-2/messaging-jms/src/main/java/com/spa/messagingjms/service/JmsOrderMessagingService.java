package com.spa.messagingjms.service;

import com.spa.messagingjms.model.TacoOrder;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jmsTemplate;
    private final Destination orderQueue;
    @Value("${spring.jms.template.default-destination}")
    private String destination;

    @Override
    public void sendOrder(TacoOrder order) {
        MessageCreator messageCreator = session -> session.createObjectMessage(order);
        jmsTemplate.send(messageCreator);
    }

    @Override
    public void sendOrderWithDest(TacoOrder order) {
        jmsTemplate.send(orderQueue, session -> session.createObjectMessage(order));
    }

    @Override
    public void sendOrderWithDestString(TacoOrder order) {
        jmsTemplate.send(destination, session -> session.createObjectMessage(order));
    }

    @Override
    public void convertAndSend(TacoOrder order) {
        jmsTemplate.convertAndSend(destination, order);
    }

    @Override
    public void sendOrderWithHeader(TacoOrder order) {
        jmsTemplate.send(destination, session -> getMessage(session, order));
    }

    @Override
    public void convertAndSendOrderWithHeader(TacoOrder order) {
        jmsTemplate.convertAndSend(destination, order, this::getMessage);
    }

    private Message getMessage(Session session, TacoOrder order) {
        Message message = null;
        try {
            message = session.createObjectMessage(order);
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
        } catch (JMSException ignored) {}
        return message;
    }

    private Message getMessage(Message message) {
        try {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
        } catch (JMSException ignored) {}
        return message;
    }
}
