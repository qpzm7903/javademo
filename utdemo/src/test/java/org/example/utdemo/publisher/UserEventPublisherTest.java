package org.example.utdemo.publisher;

import org.example.utdemo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEventPublisherTest {
    
    @Autowired
    private UserEventPublisher publisher;
    
    @Test
    public void testSend(){
        publisher.sendUserEvent(new User());
    }
}