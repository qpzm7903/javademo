package com.qpzm7903.jvmdemo;

public class StaticResolution {
    public static void sayHello() {
        System.out.println("hello jvm world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
