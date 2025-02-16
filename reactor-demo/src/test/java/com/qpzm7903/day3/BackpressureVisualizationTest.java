package com.qpzm7903.day3;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class BackpressureVisualizationTest extends BaseStockQuoteTest {

    @Test
    void visualizeBackpressureStrategies() {
        StepVerifier.withVirtualTime(() -> {
            return processor.processWithBuffer(generator.generateQuotes().take(50));
        })
        .thenAwait(Duration.ofSeconds(30))
        .expectComplete()
        .verify();

        System.out.println("\nVisualization of different backpressure strategies:");
        System.out.println("Buffer: [1][2][3][4][5]...until buffer full, then drop");
        System.out.println("Drop  : [1]...drop...[5]...drop...[9]");
        System.out.println("Latest: [1]...skip...[5]...skip...[latest]");
    }
} 