package com.qpzm7903.java8demo;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDemo {
    @Test
    public void test() {
        Map<Boolean, List<Integer>> map = IntStream
                .range(0, 100)
                .boxed()
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        for (Map.Entry<Boolean, List<Integer>> booleanListEntry : map.entrySet()) {
            System.out.println(booleanListEntry.getKey());
            booleanListEntry.getValue().forEach(System.out::println);
        }
    }

    @Test
    public void test_grouping() {

        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Student(String.valueOf(i), i % 2));
        }

        Map<Integer, List<String>> collect = list.stream()
                .collect(Collectors.groupingBy(Student::getSex, Collectors.mapping(a -> a.getName(), Collectors.toList())));

        System.out.println(collect.size());
        Set<Integer> integers = collect.keySet();
        System.out.println(integers);
    }

    public static class Student {
        String name;
        int sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Student(String name, int sex) {
            this.name = name;
            this.sex = sex;
        }
    }

    @Test
    void test_compare() {
        Map<String, String> map = new HashMap<>();
        map.put("b", "1");
        map.put("a", "2");
        map.put("a1", "3");
        List<Map.Entry<String, String>> collect = map.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<String, String> entry) -> entry.getKey())
                        .thenComparing((Map.Entry<String, String> entry) -> entry.getValue()))
                .collect(Collectors.toList());

        for (Map.Entry<String, String> entry : collect) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
