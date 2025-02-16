package com.qpzm7903.day1;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FluxCreationDemoTest {

    private FluxCreationDemo demo = new FluxCreationDemo();

    @Test
    void testFluxFromJust() {
        StepVerifier.create(demo.createFluxFromJust())
                .expectNext("Hello", "Reactor", "World")
                .verifyComplete();
    }

    @Test
    void testFluxFromIterable() {
        StepVerifier.create(demo.createFluxFromIterable())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    void testFluxFromGenerate() {
        StepVerifier.create(demo.createFluxFromGenerate())
                .expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .verifyComplete();
    }

    @Test
    void testEmptyMono() {
        StepVerifier.create(demo.createEmptyMono())
                .verifyComplete();
    }
}
