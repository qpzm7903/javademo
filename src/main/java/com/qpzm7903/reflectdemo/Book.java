package com.qpzm7903.reflectdemo;


import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.System.out;

enum Tweedle {DEE, DUM}

/**
 * @author qpzm7903
 * @since 2020-06-05-22:01
 */

public class Book {
    public long chapters = 0;
    public String[] characters = {"Alice", "White Rabbit"};
    public Tweedle twin = Tweedle.DEE;
    private int number = 0;

    public int getNumber() {
        return number;
    }

    public static void main(String... args) {
        Book book = new Book();
        String fmt = "%6S:  %-12s = %s%n";

        try {
            Class<?> c = book.getClass();

            Field chap = c.getDeclaredField("chapters");
            out.format(fmt, "before", "chapters", book.chapters);
            chap.setLong(book, 12);
            out.format(fmt, "after", "chapters", chap.getLong(book));

            Field chars = c.getDeclaredField("characters");
            out.format(fmt, "before", "characters",
                    Arrays.asList(book.characters));
            String[] newChars = {"Queen", "King"};
            chars.set(book, newChars);
            out.format(fmt, "after", "characters",
                    Arrays.asList(book.characters));

            Field t = c.getDeclaredField("twin");
            out.format(fmt, "before", "twin", book.twin);
            t.set(book, Tweedle.DUM);
            out.format(fmt, "after", "twin", t.get(book));

            Field number = c.getDeclaredField("number");
            out.format(fmt, "before", "number", book.getNumber());
            number.set(book, 10);
            out.format(fmt, "after", "number", number.getInt(book));

            // production code should handle these exceptions more gracefully
        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }
}