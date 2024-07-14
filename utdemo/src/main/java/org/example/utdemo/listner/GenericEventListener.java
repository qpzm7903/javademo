package org.example.utdemo.listner;

import org.example.utdemo.event.GenericEvent;

public interface GenericEventListener<T> {
    void handleEvent(GenericEvent<T> event);
    boolean supportsEventType(Class<?> eventType);
}
