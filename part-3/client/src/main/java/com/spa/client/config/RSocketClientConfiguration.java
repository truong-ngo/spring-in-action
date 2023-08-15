package com.spa.client.config;

import com.spa.client.model.Alert;
import com.spa.client.model.GratuityIn;
import com.spa.client.model.GratuityOut;
import com.spa.client.model.StockQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Configuration
public class RSocketClientConfiguration {
//    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder builder) {
        return args -> {
            RSocketRequester tcp = builder.tcp("localhost", 7000);
            /* Request-response */
            tcp
                    .route("greeting")
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));
            String who = "Craig";
            tcp
                    .route("greeting/{name}", who)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));

            /* Request-stream */
            String stockSymbol = "XYZ";
            tcp
                    .route("stock/{symbol}", stockSymbol)
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(st -> log.info("Price of {} : {} (at {})", st.getSymbol(), st.getPrice(), st.getTimestamp()))
                    .subscribe();

            /* Fire-and-forget */
            tcp
                    .route("alert")
                    .data(new Alert(Alert.Level.RED, "Brandon", Instant.now()))
                    .send()
                    .subscribe();
            log.info("Alert send");

            /* Chanel */
            Flux<GratuityIn> gratuityInFlux =
                    Flux
                            .fromArray(new GratuityIn[] {
                                    new GratuityIn(BigDecimal.valueOf(35.50), 18),
                                    new GratuityIn(BigDecimal.valueOf(10.00), 15),
                                    new GratuityIn(BigDecimal.valueOf(23.25), 20),
                                    new GratuityIn(BigDecimal.valueOf(52.75), 18),
                                    new GratuityIn(BigDecimal.valueOf(80.00), 15)
                            })
                            .delayElements(Duration.ofSeconds(1));
            tcp
                    .route("gratuity")
                    .data(gratuityInFlux)
                    .retrieveFlux(GratuityOut.class)
                    .subscribe(out -> log.info(String.format(
                            "%s gratuity on %s is %s",
                            out.getPercent() + "%",
                            out.getBillTotal(),
                            out.getGratuity())));
        };
    }

    @Bean
    public ApplicationRunner webSocketSender(RSocketRequester.Builder builder) {
        return args -> {
            RSocketRequester requester = builder.websocket(URI.create("ws://localhost:8080/rsocket"));
            requester
                    .route("greeting")
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(res -> log.info("Got a response: {}", res));

            String who = "Craig";
            requester
                    .route("greeting/{name}", who)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));
        };
    }
}
