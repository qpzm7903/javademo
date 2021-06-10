package com.qpzm7903;

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


}
