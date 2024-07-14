package org.example.utdemo.listner;

import org.example.utdemo.domain.PersistObject;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.listner.service.PersisObjectListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersisObjectListener extends AbstractGenericEventListener<PersistObject> {
    Logger log = LoggerFactory.getLogger(PersistObject.class);
    @Autowired
    private PersisObjectListenerService persisObjectListenerService;
    
    @Override
    protected Class<?> getEventType() {
        return PersistObject.class;
    }
    
    @Override
    public void handleEvent(GenericEvent<PersistObject> event) {
        persisObjectListenerService.handleEvent(event);
    }
    
    
}
