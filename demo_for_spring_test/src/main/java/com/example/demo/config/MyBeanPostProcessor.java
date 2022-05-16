package com.example.demo.config;

import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-14-23:00
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        log.info("receive a bean before init {}", beanName);
        if (bean instanceof TestService) {
//            throw new BeanCreationException("i dont want to init it");

            return bean;
        }
        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("receive a bean after init {}", beanName);
        return bean;
    }
}
