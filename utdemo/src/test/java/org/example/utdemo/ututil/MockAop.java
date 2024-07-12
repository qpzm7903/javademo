package org.example.utdemo.ututil;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MockAop {
    @Pointcut("execution (* org.example.utdemo.service.*.*(..))")
    public void test() {
    
    }
    
    @Before("test()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        System.out.println("hello");
    }
    
}
