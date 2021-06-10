package com.qpzm7903;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-10 22:28
 */

public class JarDemo {
    public static void main(String[] args) throws IOException {
        URL resource = CustomClassLoader.class.getClassLoader().getResource("TestModel-1.jar");
        JarFile jarFile = new JarFile(resource.getFile());
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry.getName().endsWith(".class")) {
                System.out.println(converter(jarEntry.getName()));
            }
        }

    }

    static String converter(String className) {
        className = className.substring(0, className.indexOf('.'));
        return className.replace("/", ".");
    }
}
