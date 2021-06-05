package com.qpzm7903.behaviouralpatterns.observer.template;


/**
 * 提供管理观察者的接口
 *
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 20:43
 */

public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Message message);
}
