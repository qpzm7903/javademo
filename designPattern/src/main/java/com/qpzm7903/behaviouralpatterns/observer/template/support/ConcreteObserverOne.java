package com.qpzm7903.behaviouralpatterns.observer.template.support;

import com.qpzm7903.behaviouralpatterns.observer.template.Message;
import com.qpzm7903.behaviouralpatterns.observer.template.Observer;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 20:45
 */

public class ConcreteObserverOne implements Observer {
    @Override
    public void update(Message message) {
        System.out.println("ConcreteObserverOne is notified. " + message.getMessage());
    }
}
