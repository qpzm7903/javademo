package com.qpzm7903.validation.demo.controller;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-12-17:12
 */
@Data
public class Person {
    private Integer id;

    @NotNull
    @Size(min = 6,max = 12)
    private String name;

    @NotNull
    @Min(20)
    private Integer age;
}
