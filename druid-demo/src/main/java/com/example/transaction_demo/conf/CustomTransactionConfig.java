package com.example.transaction_demo.conf;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * not work
 */
// @Configuration
public class CustomTransactionConfig {
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        proxyCreator.setBeanNames("*Controller");  // 可以指定代理哪些 Bean
        proxyCreator.setInterceptorNames("customTransactionInterceptor");
        return proxyCreator;
    }
    
    @Bean
    public TransactionInterceptor customTransactionInterceptor() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        Properties transactionAttributes = new Properties();
        
        // 例如，设置所有方法使用 REQUIRED 事务传播行为
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        
        interceptor.setTransactionManager(transactionManager);
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }
}