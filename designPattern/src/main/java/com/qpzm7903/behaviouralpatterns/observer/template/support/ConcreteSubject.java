package com.qpzm7903.behaviouralpatterns.observer.template.support;

import com.qpzm7903.behaviouralpatterns.observer.template.Message;
import com.qpzm7903.behaviouralpatterns.observer.template.Subject;
import com.qpzm7903.behaviouralpatterns.observer.template.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 20:44
 */

public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Message message) {
        observers.forEach(observer -> observer.update(message));
    }
}
