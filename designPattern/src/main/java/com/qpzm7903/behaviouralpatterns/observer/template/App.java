package com.qpzm7903.behaviouralpatterns.observer.template;

import com.qpzm7903.behaviouralpatterns.observer.template.support.ConcreteObserverOne;
import com.qpzm7903.behaviouralpatterns.observer.template.support.ConcreteObserverTwo;
import com.qpzm7903.behaviouralpatterns.observer.template.support.ConcreteSubject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 20:49
 */

public class App {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        // 注册观察者
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());

        // 通知观察者
        subject.notifyObservers(new Message.DefaultMessage("hello"));

    }
}
