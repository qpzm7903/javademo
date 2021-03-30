package com.qpzm7903.jvmdemo;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myLoader = new MyClassLoader();
        Object object = myLoader.loadClass("com.qpzm7903.jvmdemo.ClassLoaderTest").newInstance();
        System.out.println(object.getClass());
        System.out.println(object instanceof com.qpzm7903.jvmdemo.ClassLoaderTest);

        System.out.println(myLoader.getParent().toString());
        System.out.println(object.getClass().getClassLoader());
        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        System.out.println(classLoaderTest.getClass().getClassLoader());

    }

    static class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                String fileName = name.substring(name.lastIndexOf(".") + 1 ) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }
                byte[] b = new byte[is.available()];
                is.read(b);
                return defineClass(name, b, 0, b.length);

            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}
