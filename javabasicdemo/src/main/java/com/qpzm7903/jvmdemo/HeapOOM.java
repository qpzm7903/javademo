package com.qpzm7903.jvmdemo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author qpzm7903
 * @since 2022-11-11-22:54
 */
public class HeapOOM {
    static class OOMObject{
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        Random random = new Random();
        while (true) {
            OOMObject test = new OOMObject();
            test.setName("test" + random.nextInt());
            list.add(test);
        }
    }
}
