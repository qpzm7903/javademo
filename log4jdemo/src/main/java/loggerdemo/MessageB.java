package loggerdemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-01-22:55
 */
public class MessageB {
    private static final Log log4j = LogFactory.getLog(MessageB.class);

    public static void test() {

        log4j.info("test log from message B");
    }
}
