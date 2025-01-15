package org.example.utdemo.event;

import org.springframework.context.ApplicationEvent;

public class GenericEvent<T> extends ApplicationEvent {
    private T data;
    
    public GenericEvent(Object source, T data) {
        super(source);
        this.data = data;
    }
    
    public T getData() {
        return data;
    }
}
