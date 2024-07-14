package org.example.utdemo.listner;

import org.example.utdemo.event.GenericEvent;

import java.util.concurrent.ExecutorService;

public interface GenericEventListener<T> {
    void handleEvent(GenericEvent<T> event);
    
    boolean supportsEventType(Class<?> eventType);
    
    boolean isAsync();
    ExecutorService getThreadPool();
    
    
}
