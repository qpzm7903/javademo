package com.qpzm7903.proxyPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *
 * @author qpzm7903
 * @since 2020-06-12-6:33
 */

public class IUserControllerDynamicProxy {
    public Object createProxy(Object proxyObject) {
        Class<?>[] interfaces = proxyObject.getClass().getInterfaces();
        DynamicProxyHandler handler = new DynamicProxyHandler(proxyObject);
        return Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(), interfaces, handler);
    }

    private static class DynamicProxyHandler implements InvocationHandler {

        private Object proxyObject;

        public DynamicProxyHandler(Object proxyObject) {
            this.proxyObject = proxyObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long startTimeStamp = System.currentTimeMillis();
            Object result = method.invoke(proxyObject, args);
            long endTimeStamp = System.currentTimeMillis();
            String apiName = proxyObject.getClass().getName() + ":" + method.getName();
            System.out.println("invoke api:" + apiName + "  cost " + (endTimeStamp - startTimeStamp) + "ms");
            return result;
        }
    }

    public static void main(String[] args) {
        IUserControllerDynamicProxy proxy = new IUserControllerDynamicProxy();
        IUserController userController = (IUserController) proxy.createProxy(new IUserControllerImpl());
        userController.login("test", "test");
        userController.register("test", "test");

    }


}
