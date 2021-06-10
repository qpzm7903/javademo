package com.qpzm7903;

import com.qpzm7903.annotation.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-08 22:08
 */
public class App {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        String pkgName = "com.qpzm7903";
        String pkgPath = AnnotationScannerUtils.getPkgPath(pkgName);
        log.info(String.format("pkgPath is {%s}", pkgName));


        Map<Class<? extends Annotation>, Set<Class<?>>> classesMap = AnnotationScannerUtils.scanClassesByAnnotations(pkgName, pkgPath, true,
                Collections.singletonList(Component.class));
        if (classesMap.size() == 0) {
            log.error(String.format("Not exists any class in {%s} with the specified annotation", pkgPath));
            return;
        }

        Set<Class<?>> classSet = new HashSet<>();
        classesMap.forEach((k, v) -> {
            log.info("get {} classes with {}", v.size(), k.getSimpleName());
            classSet.addAll(v);
        });

    }
}
