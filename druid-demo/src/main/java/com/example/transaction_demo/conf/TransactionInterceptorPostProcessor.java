package com.example.transaction_demo.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * not work
 */
// @Component
public class TransactionInterceptorPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TransactionInterceptor) {
            // 在初始化前对 TransactionInterceptor 进行处理
            System.out.println("Modifying TransactionInterceptor before initialization: " + beanName);
            // 可以在这里增加自定义逻辑，比如修改属性或添加日志
        }
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TransactionInterceptor) {
            // 在初始化后对 TransactionInterceptor 进行处理
            System.out.println("Modifying TransactionInterceptor after initialization: " + beanName);
            // 可以在这里增加自定义逻辑或对拦截器的行为进行调整
        }
        return bean;
    }
}
