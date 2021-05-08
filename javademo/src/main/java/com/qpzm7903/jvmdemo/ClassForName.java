package com.qpzm7903.jvmdemo;

public class ClassForName {

    static {
        System.out.println("静态代码块");
    }

    private static String staticFiled = staticMethod();

    private static String staticMethod() {
        System.out.println("执行静态方法");
        return "静态字段赋值";
    }
}
