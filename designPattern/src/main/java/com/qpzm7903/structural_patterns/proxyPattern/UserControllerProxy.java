package com.qpzm7903.structural_patterns.proxyPattern;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author qpzm7903
 * @since 2020-06-12-7:07
 */

public class UserControllerProxy  implements MethodInterceptor {

    private Object target;

    public UserControllerProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(target.getClass());

        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        long startTimeStamp = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTimeStamp = System.currentTimeMillis();
        String apiName = target.getClass().getName() + ":" + method.getName();
        System.out.println("invoke api:" + apiName + "  cost " + (endTimeStamp - startTimeStamp) + "ms");
        return result;
    }

    public static void main(String[] args) {
        UserController userController = (UserController) new UserControllerProxy(new UserController()).getProxyInstance();
        userController.login("test", "test");
        userController.register("test", "test");
    }
}
