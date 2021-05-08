package com.qpzm7903.jvmdemo;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        getPrintlnMH(obj).invokeExact("hello");

    }

    private static MethodHandle getPrintlnMH(Object reveiver) throws NoSuchMethodException, IllegalAccessException {
        // 方法类型，第一个参数时返回拉欸行，第二个，以及后面都是入参
        MethodType methodType = MethodType.methodType(void.class, String.class);
        // lookup 是查找对应类中符合的方法
        // bindTo 调用虚方法，第一个参数是隐式的this，所以用bindTo来完成
        return lookup().findVirtual(reveiver.getClass(), "println", methodType).bindTo(reveiver);
    }
}
