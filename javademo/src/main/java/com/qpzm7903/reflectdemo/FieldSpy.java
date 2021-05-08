package com.qpzm7903.reflectdemo;

/**
 * @author qpzm7903
 * @since 2020-06-03-22:05
 */

import java.lang.reflect.Field;
import java.util.List;

public class FieldSpy<T> {
    public boolean[][] b = {{false, false}, {true, true}};
    public String name = "Alice";
    public List<Integer> list;
    public T val;

    public static void main(String... args) {
        try {
            String[] names = {"b", "name", "list", "val"};
            FieldSpy<FieldSpy> fieldSpy = new FieldSpy();
            Class<?> c = fieldSpy.getClass();
            for (String name : names) {
                Field f = c.getField(name);
                System.out.format("Type: %s%n", f.getType());
                System.out.format("GenericType: %s%n", f.getGenericType());
                System.out.println();
            }


            // production code should handle these exceptions more gracefully
        } catch (
                NoSuchFieldException x) {
            x.printStackTrace();
        }
    }
}