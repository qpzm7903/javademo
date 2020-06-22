package com.qpzm7903.reflectdemo;

/**
 * @author qpzm7903
 * @since 2020-06-06-21:37
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import static java.lang.System.err;
import static java.lang.System.out;

public class Deet<T> {
    private boolean testDeet(Locale l) {
        // getISO3Language() may throw a MissingResourceException
        out.format("Locale = %s, ISO Language Code = %s%n", l.getDisplayName(), l.getISO3Language());
        return true;
    }

    private int testFoo(Locale l) {
        return 0;
    }

    private boolean testBar() {
        return true;
    }

    public static void main(String... args) {
        out.format("Usage: java Deet <classname> <langauge> <country> <variant>%n");


        String[] arg = {"reflectdemo.Deet", "ja", "JP", "JP"};

        try {
            Class<?> c = Class.forName(arg[0]);
            Object t = c.newInstance();

            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                String mname = m.getName();
                if (!mname.startsWith("test")
                        || (m.getGenericReturnType() != boolean.class)) {
                    continue;
                }
                Type[] pType = m.getGenericParameterTypes();
                if ((pType.length != 1)
                        || Locale.class.isAssignableFrom(pType[0].getClass())) {
                    continue;
                }

                out.format("invoking %s()%n", mname);
                try {
                    m.setAccessible(true);
                    Object o = m.invoke(t, new Locale(arg[1], arg[2], arg[3]));
                    out.format("%s() returned %b%n", mname, (Boolean) o);

                    // Handle any exceptions thrown by method to be invoked.
                } catch (InvocationTargetException x) {
                    Throwable cause = x.getCause();
                    err.format("invocation of %s failed: %s%n",
                            mname, cause.getMessage());
                }
            }

            // production code should handle these exceptions more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }
}