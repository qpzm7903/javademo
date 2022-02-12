package com.qpzm7903.java8demo.data_and_time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-22:11
 */
class DataTest {

    @Test
    void test_localDate() {
        LocalDate of = LocalDate.of(2012, 12, 12);
        System.out.println(of);
        System.out.println(of.getYear());
        System.out.println(of.getMonth());
        System.out.println(of.getDayOfMonth());
        System.out.println(of.getDayOfWeek());
        System.out.println(of.getDayOfYear());
        System.out.println(of.isLeapYear());
    }

    @Test
    void test_localData_now() {
        System.out.println(LocalDate.now());
    }

    @Test
    void test_localDat_and_chronoField() {
        LocalDate now = LocalDate.now();
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));

    }

    @Test
    void test_localTime() {
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }

    @Test
    void test_localTime_with_() {
        LocalTime of = LocalTime.of(12, 12, 12);
        System.out.println(of);
    }

    @Test
    void test_localTime_parse() {
        LocalTime parse = LocalTime.parse("12:12:12");
        LocalTime of = LocalTime.of(12, 12, 12);
        Assertions.assertEquals(parse, of);
    }

    @Test
    void test_localDataTime(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    @Test
    void test_localDateTime() {
        LocalDateTime of = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        System.out.println(of);
    }

    @Test
    void test_DateTmeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        String format = now.format(formatter);
        System.out.println(format);
        LocalDate parse = LocalDate.parse(format, formatter);
        Assertions.assertEquals(parse, now);
    }

    @Test
    void test_zoneDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(dateTime); // 2021-12-28T06:44:20.063
        System.out.println(zonedDateTime); // 2021-12-28T06:44:20.063+08:00[Asia/Shanghai]
    }

    @Test
    void test_instant() {
        System.out.println(Instant.MIN);
        System.out.println(Instant.now());
        System.out.println(Instant.MAX);
    }

}