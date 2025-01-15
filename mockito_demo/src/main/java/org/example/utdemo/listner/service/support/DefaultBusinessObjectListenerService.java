package org.example.utdemo.listner.service.support;

import org.example.utdemo.domain.BusinessObject;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.listner.service.BusinessObjectListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultBusinessObjectListenerService implements BusinessObjectListenerService {
    
    Logger LOGGER = LoggerFactory.getLogger(DefaultBusinessObjectListenerService.class);
    
    @Override
    public void handleEvent(GenericEvent<BusinessObject> event) {
        LOGGER.info("");
    }
}
