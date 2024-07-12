package org.example.utdemo.event;

import org.springframework.context.ApplicationEvent;

public class TestEvent<T> extends ApplicationEvent {
    private T what;
    
    public TestEvent(Object source, T what) {
        super(source);
        this.what = what;
    }
    
    public T getWhat() {
        return what;
    }
}
