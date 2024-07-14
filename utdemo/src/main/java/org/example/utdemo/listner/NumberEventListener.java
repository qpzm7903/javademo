package org.example.utdemo.listner;

import jakarta.annotation.PostConstruct;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NumberEventListener implements GenericEventListener<Number> {
    private int count = 0;

    @EventListener
    public void handleInteger(GenericEvent<Integer> genericEvent) {
        System.out.println("rec integer event " + genericEvent.getData());
        count++;
    }
    @PostConstruct
    public void init() {
        dispatcher.registerListener(this);
    }

    @Autowired
    private GenericEventDispatcher dispatcher;

    @Override
    public void handleEvent(GenericEvent<Number> event) {
        System.out.println("Received Number event: " + event.getData());
    }

    @Override
    public boolean supportsEventType(Class<?> eventType) {
        return Number.class.isAssignableFrom(eventType);
    }
    public int getCount() {
        return count;
    }
}
