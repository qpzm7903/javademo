package com.example.transaction_demo.conf;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 在这种方式下，容器里其实存在两个拦截器，一个是springboot内置的，一个是我们下面初始化的，还没分析过优先级。
 * <p></p>
 * 最简单的方式就是让两个拦截器都命中，然后打断点看看就知道了。
 */
// @Configuration
public class TransactionConfig2 {
    private final PlatformTransactionManager transactionManager;
    
    public TransactionConfig2(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    @Bean
    public Advisor transactionAdvisor() {
        // 定义切入点，匹配以 'update' 开头的方法
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example..*.update*(..))");
        
        // 定义事务属性
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
        
        // 创建事务拦截器
        TransactionInterceptor txInterceptor = new TransactionInterceptor(transactionManager, transactionAttributes);
        
        // 创建并返回顾问
        return new DefaultPointcutAdvisor(pointcut, txInterceptor);
    }
}
