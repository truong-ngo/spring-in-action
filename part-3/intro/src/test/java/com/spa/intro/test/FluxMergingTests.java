package com.spa.intro.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class FluxMergingTests {
    public Flux<String> getCharFlux() {
        return Flux.just("Garfield", "Kojak", "Barbosa");
    }

    public Flux<String> getFoodFlux() {
        return Flux.just("Lasagna", "Lollipops", "Apples");
    }

    @Test
    public void mergeFluxes() {
        Flux<String> charFlux = getCharFlux().delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = getFoodFlux().delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        Flux<String> mergeFlux = charFlux.mergeWith(foodFlux);
        StepVerifier.create(mergeFlux)
                .expectNext("Garfield")
                .expectNext("Lasagna")
                .expectNext("Kojak")
                .expectNext("Lollipops")
                .expectNext("Barbosa")
                .expectNext("Apples")
                .verifyComplete();
    }

    @Test
    public void zipFluxes() {
        Flux<String> charFlux = getCharFlux();
        Flux<String> foodFlux = getFoodFlux();
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(charFlux, foodFlux);
        StepVerifier.create(zippedFlux)
                .expectNextMatches(p -> p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
                .expectNextMatches(p -> p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
                .expectNextMatches(p -> p.getT1().equals("Barbosa") && p.getT2().equals("Apples"))
                .verifyComplete();
    }

    @Test
    public void zipFluxesToObject() {
        Flux<String> charFlux = getCharFlux();
        Flux<String> foodFlux = getFoodFlux();
        Flux<String> zippedFlux = Flux.zip(charFlux, foodFlux, (c, f) -> String.format("%s eats %s", c, f));
        StepVerifier.create(zippedFlux)
                .expectNext("Garfield eats Lasagna")
                .expectNext("Kojak eats Lollipops")
                .expectNext("Barbosa eats Apples")
                .verifyComplete();
    }
}
