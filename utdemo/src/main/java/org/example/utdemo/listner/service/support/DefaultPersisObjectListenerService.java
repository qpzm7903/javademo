package org.example.utdemo.listner.service.support;

import org.example.utdemo.domain.PersistObject;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.listner.service.PersisObjectListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultPersisObjectListenerService implements PersisObjectListenerService {
    
    Logger log = LoggerFactory.getLogger(DefaultPersisObjectListenerService.class);
    
    @Override
    public void handleEvent(GenericEvent<PersistObject> event) {
        
        log.info("PersisObjectListener handleEvent {}", event.getData());
    }
}
