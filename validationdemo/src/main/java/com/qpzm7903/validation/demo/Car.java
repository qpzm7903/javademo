package com.qpzm7903.validation.demo;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-22:35
 */
@Data
public class Car {
    @NotNull
    private String manufacturer;

    @AssertTrue
    private boolean isRegistered;

    public Car(String manufacturer, boolean isRegistered) {
        this.manufacturer = manufacturer;
        this.isRegistered = isRegistered;
    }
}
