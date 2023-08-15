package com.spa.simpleflow.config;

import com.spa.simpleflow.integration.EmailToOrderTransformer;
import com.spa.simpleflow.integration.OrderSubmitMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class TacoEmailIntegrationConfig {
    @Bean
    public IntegrationFlow tacoOrderEmailFlow(EmailProperties emailProperties, EmailToOrderTransformer transformer, OrderSubmitMessageHandler handler) {
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()), e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
