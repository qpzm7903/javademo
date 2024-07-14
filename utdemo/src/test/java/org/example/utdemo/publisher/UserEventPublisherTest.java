package org.example.utdemo.publisher;

import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.example.utdemo.listner.UserListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class UserEventPublisherTest {
    
    @Autowired
    private UserEventPublisher publisher;

    @Autowired
    private UserListener testListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private GenericEventDispatcher genericEventDispatcher;
    
    @Test
    public void testSend(){

        genericEventDispatcher.dispatch(new GenericEvent<>(this, new User()));
        genericEventDispatcher.dispatch(new GenericEvent<>(this, Integer.valueOf(1)));
        genericEventDispatcher.dispatch(new GenericEvent<>(this, "1"));

        Assertions.assertTrue( testListener.getCount() > 0);
    }
}