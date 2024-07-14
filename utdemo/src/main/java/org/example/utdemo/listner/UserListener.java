package org.example.utdemo.listner;

import jakarta.annotation.PostConstruct;
import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListener implements GenericEventListener<User> {
    private int count = 0;

    @Override
    public void handleEvent(GenericEvent<User> event) {
        System.out.println("rec user event " + event.getData());
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
    public boolean supportsEventType(Class<?> eventType) {
        return User.class.isAssignableFrom(eventType);
    }
}
