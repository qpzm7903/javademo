package loggerdemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-01-22:54
 */
public class MessageA {
    private static final Log log4j = LogFactory.getLog(MessageA.class);

    public static void test() {
        log4j.info("test log from message A");

    }
}
