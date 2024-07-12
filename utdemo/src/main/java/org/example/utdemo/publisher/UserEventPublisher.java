package org.example.utdemo.publisher;

import org.example.utdemo.domain.User;
import org.example.utdemo.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    void sendUserEvent(User user) {
        TestEvent<User> event = new TestEvent<>(this, user);
        
        applicationEventPublisher.publishEvent(event);
    }
    
}
