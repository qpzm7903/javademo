package com.qpzm7903.java8demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

public class DishTest {
    private List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));


    @Test
    public void test() {
        List<String> collect = menu.stream()
                .filter(d -> {
                    System.out.println("filtering " + d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                    System.out.println("mapping " + d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(toList());

        System.out.println(collect);
    }

    @Test
    public void test_map() {
        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);
    }

    @Test
    public void test_flatmap() {
        List<String[]> collect = Stream
                .of("hello", "world")
                .map(word -> word.split(""))
                .collect(toList());

        List<Stream<String>> collect1 = Stream
                .of("hello", "world")
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .collect(toList());

        List<String> collect2 = Stream
                .of("hello", "world")
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .collect(toList());

    }

    @Test
    public void test_reduce() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer reduce = integers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(reduce);

        Optional<Integer> reduce1 = integers.stream().reduce((a, b) -> a + b);

        Optional<Integer> max = integers.stream().reduce((a, b) -> a > b ? a : b);
        System.out.println(max.get());
        max = integers.stream().reduce(Integer::max);
        System.out.println(max.get());


        Optional<Integer> min = integers.stream().reduce((a, b) -> a > b ? b : a);
        System.out.println(min.get());
        min = integers.stream().reduce(Integer::min);
        System.out.println(min.get());


    }


    @Test
    public void test_range_close() {
        System.out.println(IntStream.rangeClosed(1, 100).reduce(0, Integer::sum));
        System.out.println(IntStream.range(1, 100).reduce(0, Integer::sum));
    }

    @Test
    public void test_iterate() {
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void test_fabonaqi() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println(t[1]));
    }

}