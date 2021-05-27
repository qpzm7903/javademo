package com.qpzm7903.java8demo;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OptionalDemoTest {


    @Data
    public static class Person{
        private Optional<Car> car;

    }

    @Data
    public static class Car{
        private Optional<Insurance> insurance;
    }

    @Data
    public static class Insurance{
        private String name;
    }

    @Test
    public void test() {
        Person person = new Person();
        Optional<Person> optionalPerson = Optional.of(person);
        Optional<Optional<Car>> optionalOptionalCar = optionalPerson.map(Person::getCar);
//        optionalOptionalCar.map(Car::getInsurance); // error
        Optional<Car> optionalCar = optionalPerson.flatMap(Person::getCar);
        Optional<Insurance> insurance = optionalCar.flatMap(Car::getInsurance);
        String insuranceName = insurance.map(Insurance::getName).orElse("unKnow");
    }
}
