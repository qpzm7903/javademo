package org.example.utdemo.listner;

import org.example.utdemo.event.GenericEvent;
import org.springframework.stereotype.Component;

@Component
public class StringListener extends AbstractGenericEventListener<String> {
    private int count = 0;
    
    public int getCount() {
        return count;
    }
    
    
    @Override
    public void handleEvent(GenericEvent<String> event) {
        System.out.println("Received String event: " + event.getData());
        count++;
    }
    
    @Override
    protected Class<?> getEventType() {
        return String.class;
    }
}
