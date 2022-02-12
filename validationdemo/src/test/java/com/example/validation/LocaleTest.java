package com.example.validation;

import org.junit.jupiter.api.Test;
import sun.security.action.GetPropertyAction;

import java.security.AccessController;
import java.util.Locale;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-09-19:56
 */
public class LocaleTest {
    @Test
    void test_() {
        Locale aDefault = Locale.getDefault();
        GetPropertyAction en1 = new GetPropertyAction("user.language", "en");
        System.out.println(en1);
        System.out.println(en1.run());
        String en = AccessController.doPrivileged(en1);
        // for compatibility, check for old user.region property
        String s = AccessController.doPrivileged(new GetPropertyAction("user.region"));

        System.out.println(en);
        System.out.println(s);

    }
}
