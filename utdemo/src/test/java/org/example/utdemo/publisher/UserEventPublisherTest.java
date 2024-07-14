package org.example.utdemo.publisher;

import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.example.utdemo.listner.NumberEventListener;
import org.example.utdemo.listner.StringListener;
import org.example.utdemo.listner.UserListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserEventPublisherTest {
    
    @Autowired
    private UserListener testListener;
    
    @Autowired
    private StringListener stringListener;
    
    @Autowired
    private NumberEventListener numberEventListener;
    
    @Autowired
    private GenericEventDispatcher genericEventDispatcher;
    
    @Test
    public void testSend(){

        genericEventDispatcher.dispatch(new GenericEvent<>(this, new User()));
        genericEventDispatcher.dispatch(new GenericEvent<>(this, 1));
        genericEventDispatcher.dispatch(new GenericEvent<>(this, "1"));
        
        Assertions.assertEquals(1, testListener.getCount());
        Assertions.assertEquals(1, stringListener.getCount());
        Assertions.assertEquals(1, numberEventListener.getCount());
    }
}