package org.example.utdemo.listner;

import org.example.utdemo.event.GenericEvent;
import org.springframework.stereotype.Component;

@Component
public class NumberEventListener extends AbstractGenericEventListener<Number> {
    private int count = 0;
    
    @Override
    public void handleEvent(GenericEvent<Number> event) {
        System.out.println("Received Number event: " + event.getData());
        count++;
    }
    
    @Override
    protected Class<?> getEventType() {
        return Number.class;
    }
    
    public int getCount() {
        return count;
    }
}
