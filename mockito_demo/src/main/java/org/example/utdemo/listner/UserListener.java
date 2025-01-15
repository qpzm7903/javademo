package org.example.utdemo.listner;

import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.springframework.stereotype.Component;

@Component
public class UserListener extends AbstractGenericEventListener<User> {
    private int count = 0;
    
    @Override
    public void handleEvent(GenericEvent<User> event) {
        System.out.println("rec user event " + event.getData());
        count++;
    }
    
    public int getCount() {
        return count;
    }
    
    
    @Override
    protected Class<?> getEventType() {
        return User.class;
    }
}
