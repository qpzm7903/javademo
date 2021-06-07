package com.qpzm7903.behavioural_patterns.observer.template.support;

import com.qpzm7903.behavioural_patterns.observer.template.Message;
import com.qpzm7903.behavioural_patterns.observer.template.Observer;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 20:46
 */

public class ConcreteObserverTwo implements Observer {

    @Override
    public void update(Message message) {
        System.out.println("ConcreteObserverTwo is notified. " + message.getMessage());

    }
}
