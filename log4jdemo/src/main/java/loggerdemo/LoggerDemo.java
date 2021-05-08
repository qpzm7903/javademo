package loggerdemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-08 20:06
 */

public class LoggerDemo {
    private static final Log log4j = LogFactory.getLog(LoggerDemo.class);

    public static void main(String[] args) {
        log4j.error("error log from log4j");
        log4j.info("info log from log4j");
        log4j.warn("warn log from log4j");
        log4j.trace("trace log from log4j");
        log4j.fatal("fatal log from log4j");
    }
}
