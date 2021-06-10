package com.qpzm7903;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-09 22:55
 */

public class CustomClassLoader extends URLClassLoader {


    public CustomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        URL resource = CustomClassLoader.class.getClassLoader().getResource("TestModel-1.jar");
        URL[] urls = new URL[1];
        urls[0] = resource;
        CustomClassLoader customClassLoader = new CustomClassLoader(urls, CustomClassLoader.class.getClassLoader());
        Class<?> aClass = customClassLoader.loadClass("com.qpzm7903.component.EmployeeService");
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }


}
