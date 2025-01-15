package com.qpzm7903;

import java.io.IOException;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-05-10:28
 */
public class HelloWorld {
    public static void main(String[] args) {
        try {
            foo();
        } catch (NullPointerException e) {
            System.out.println(e);

        } catch (IOException e) {
            System.out.println(e);

        }

        try {
            foo();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void foo() throws IOException {

    }

}
