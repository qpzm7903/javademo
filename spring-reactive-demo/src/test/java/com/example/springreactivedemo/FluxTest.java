package com.example.springreactivedemo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-20-17:19
 */
public class FluxTest {
    @Test
    void test() {
        System.out.println(Thread.currentThread().getName());

        Flux<Integer> range = Flux.range(1, 10);
        range.subscribe(index -> {
            System.out.println(index);
            System.out.println(Thread.currentThread().getName());

        });
        range.subscribe(index -> {
            System.out.println(index);
            System.out.println(Thread.currentThread().getName());
        });
    }

    @Test
    void test_exception() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error));
    }

    class SampleSubscriber<T> extends BaseSubscriber<T> {

        public void hookOnSubscribe(Subscription subscription) {
            System.out.println("Subscribed");
            request(1);
        }

        public void hookOnNext(T value) {
            System.out.println(value);
            request(1);
        }
    }

    @Test
    void test_2() {
        Flux.range(1, 10)
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(5);
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        System.out.println("Cancelling after having received " + integer);
                        cancel();

                    }
                });
    }

    @Test
    void test_3() {
        Flux.range(1, 10).limitRate(1).subscribe(System.out::println);
    }

    @Test
    void test_generate_1() {
        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });
        flux.subscribe(System.out::println);
    }

    @Test
    void test_generate_1_1() {
        Callable<Integer> stateSupplier = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 0;
            }
        };
        BiFunction<Integer, SynchronousSink<String>, Integer> generator = new BiFunction<Integer, SynchronousSink<String>, Integer>() {
            @Override
            public Integer apply(Integer state, SynchronousSink<String> sink) {
                sink.next("3 x " + state + " = " + 3 * state);
                if (state == 10) sink.complete();
                return state + 1;
            }
        };
        Flux<String> flux = Flux.generate(stateSupplier, generator);
        flux.subscribe(System.out::println);
    }

    interface MyEventListener<T> {
        void onDataChunk(List<T> chunk);

        void processComplete();
    }

    @Test
    void test_create_1() {
        List<MyEventListener<String>> myEventProcessor = new LinkedList<>();
        Flux<String> bridge = Flux.create(sink -> {
            myEventProcessor.add(
                    new MyEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
                            for (String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
        });
        bridge.subscribe(System.out::println);
        for (MyEventListener myEventListener : myEventProcessor) {
            myEventListener.onDataChunk(Stream.of("1", "2", "3", "4").collect(Collectors.toList()));

        }
    }

    @Test
    void test_thread_1() throws InterruptedException {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                )
        );
        t.start();
        t.join();

    }

    @Test
    void test_scheduler() throws InterruptedException {
        Flux.interval(Duration.ofMillis(300)).subscribe(System.out::println);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(300);
        }
    }

    @Test
    void test_scheduler2() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i + " " + Thread.currentThread().getName())
                .publishOn(s)
                .map(i -> "value " + i + " " + Thread.currentThread().getName());

        new Thread(() -> flux.subscribe(x -> System.out.println(x + " " + Thread.currentThread().getName()))).start();

        Thread.sleep(100);
    }

    @Test
    void test_subscribeOn_scheduler() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i + " " + Thread.currentThread().getName())
                .subscribeOn(s)
                .map(i -> "value " + i + " " + Thread.currentThread().getName());

        new Thread(() -> flux.subscribe(x -> System.out.println(x + " " + Thread.currentThread().getName()))).start();

        Thread.sleep(100);
    }

    @Test
    void test_() throws InterruptedException {

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i + " " + Thread.currentThread().getName());

        new Thread(() -> flux.subscribe(x -> System.out.println(x + " " + Thread.currentThread().getName()))).start();

        Thread.sleep(100);
    }
}
