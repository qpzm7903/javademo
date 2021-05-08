package com.qpzm7903.jvmdemo;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class InvokeDynamicTest {
    static class GrandFather{
        void thinking() throws Throwable {
            System.out.println("i am grand father");
        }
    }

    static class Father extends GrandFather {
        void thinking() throws Throwable {
            System.out.println("i am  father");
        }
    }

    static class Son extends Father {
        void thinking() throws Throwable {
            // 调用祖父类的方法
//            super.super.thingking();

            //
            MethodType mt = MethodType.methodType(void.class);
            MethodHandle mh = lookup().findSpecial(GrandFather.class,"thinking",mt,getClass());
            mh.invoke(this);
        }
    }

    public static void main(String[] args) throws Throwable {
        new Son().thinking();;
    }

}
