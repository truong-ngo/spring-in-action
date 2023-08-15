package com.spa.consumer.listener;

import com.spa.consumer.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Profile("jms-listener")
@Component
@RequiredArgsConstructor
public class JmsOrderListener {
    private final KitchenUI ui;

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder order) {
        ui.displayOrder(order);
    }
}
