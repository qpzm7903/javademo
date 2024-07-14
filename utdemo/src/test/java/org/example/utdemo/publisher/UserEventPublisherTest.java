package org.example.utdemo.publisher;

import org.example.utdemo.domain.Document;
import org.example.utdemo.domain.FileModel;
import org.example.utdemo.domain.User;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.event.GenericEventDispatcher;
import org.example.utdemo.listner.NumberEventListener;
import org.example.utdemo.listner.StringListener;
import org.example.utdemo.listner.UserListener;
import org.example.utdemo.listner.service.BusinessObjectListenerService;
import org.example.utdemo.listner.service.PersisObjectListenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

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
    
    @SpyBean
    private PersisObjectListenerService persisObjectListenerService;
    
    @SpyBean
    private BusinessObjectListenerService businessObjectListenerService;
    
    @Test
    public void testSendInteger() {
        
        genericEventDispatcher.dispatch(new GenericEvent<>(this, 1));
        
        Assertions.assertEquals(1, numberEventListener.getCount());
    }
    
    @Test
    public void testSendString() {
        
        genericEventDispatcher.dispatch(new GenericEvent<>(this, "1"));
        
        Assertions.assertEquals(1, stringListener.getCount());
    }
    
    
    @Test
    public void testSendUser() {
        
        genericEventDispatcher.dispatch(new GenericEvent<>(this, new User()));
        
        Assertions.assertEquals(1, testListener.getCount());
        Mockito.verify(persisObjectListenerService, Mockito.times(1))
                .handleEvent(Mockito.any());
    }
    
    @Test
    public void testBusinessEvent() {
        genericEventDispatcher.dispatch(new GenericEvent<>(this, new Document()));
        Mockito.verify(persisObjectListenerService, Mockito.times(1))
                .handleEvent(Mockito.any());
        Mockito.verify(businessObjectListenerService, Mockito.times(1))
                .handleEvent(Mockito.any());
    }
    
    @Test
    public void testBasicObjectAndBusinessObject() {
        genericEventDispatcher.dispatch(new GenericEvent<>(this, new FileModel()));
        genericEventDispatcher.dispatch(new GenericEvent<>(this, new Document()));
        Mockito.verify(persisObjectListenerService, Mockito.times(2))
                .handleEvent(Mockito.any());
        Mockito.verify(businessObjectListenerService, Mockito.times(1))
                .handleEvent(Mockito.any());
    }
}