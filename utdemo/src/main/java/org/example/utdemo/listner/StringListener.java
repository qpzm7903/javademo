package org.example.utdemo.listner;

import jakarta.annotation.PostConstruct;
import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StringListener implements GenericEventListener<String> {
    private int count = 0;

    @EventListener
    public void handleString(GenericEvent<String> stringEvent) {
        System.out.println("rec string event " + stringEvent.getData());
        count++;
    }


    public int getCount() {
        return count;
    }


    @PostConstruct
    public void init() {
        dispatcher.registerListener(this);
    }

    @Autowired
    private GenericEventDispatcher dispatcher;

    @Override
    public void handleEvent(GenericEvent<String> event) {
        System.out.println("Received String event: " + event.getData());
    }

    @Override
    public boolean supportsEventType(Class<?> eventType) {
        return String.class.isAssignableFrom(eventType);
    }
}
