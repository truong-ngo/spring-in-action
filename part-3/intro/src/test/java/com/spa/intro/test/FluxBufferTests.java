package com.spa.intro.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FluxBufferTests {
    @Test
    public void buffer() {
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana"))
                .expectNext(Arrays.asList("kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void bufferAndFlatMap() throws Exception {
        Flux.just("apple", "orange", "banana", "kiwi", "strawberry").buffer(3)
                .flatMap(x -> Flux.fromIterable(x)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        .log()).subscribe();
    }

    @Test
    public void collectList() {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
        Mono<List<String>> fruitListMono = fruitFlux.collectList();
        StepVerifier
                .create(fruitListMono)
                .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void collectMap() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> mapMono = animalFlux.collectMap(a -> a.charAt(0));
        StepVerifier
                .create(mapMono)
                .expectNextMatches(m -> {
                    return
                            m.size() == 3 &&
                            m.get('a').equals("aardvark") &&
                            m.get('e').equals("eagle") &&
                            m.get('k').equals("kangaroo");
                })
                .verifyComplete();
    }

    @Test
    public void all() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();
        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
        StepVerifier.create(hasKMono)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void any() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));
        StepVerifier.create(hasTMono)
                .expectNext(true)
                .verifyComplete();
        Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
        StepVerifier.create(hasZMono)
                .expectNext(false)
                .verifyComplete();
    }
}
