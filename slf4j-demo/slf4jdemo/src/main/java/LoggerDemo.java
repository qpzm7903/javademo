import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-08 20:06
 */

public class LoggerDemo {

    private static final Logger slf4j = LoggerFactory.getLogger(LoggerDemo.class);

    public static void main(String[] args) {
        slf4j.info("info log from slf4j");
        slf4j.debug("debug info from slf4j");
        slf4j.error("error info from slf4j");
        slf4j.warn("warn info from slf4j");
        slf4j.error("error info from slf4j");

    }
}
