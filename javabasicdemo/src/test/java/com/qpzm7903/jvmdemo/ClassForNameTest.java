package com.qpzm7903.jvmdemo;

import org.junit.jupiter.api.Test;

class ClassForNameTest {


    @Test
    public void test() {
        try {
            Class.forName("com.qpzm7903.jvmdemo.ClassForName");
            System.out.println("#########分割符(上面是Class.forName的加载过程，下面是ClassLoader的加载过程)##########");
            ClassLoader.getSystemClassLoader().loadClass("com.qpzm7903.jvmdemo.ClassForName");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}