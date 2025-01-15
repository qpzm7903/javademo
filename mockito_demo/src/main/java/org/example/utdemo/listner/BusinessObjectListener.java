package org.example.utdemo.listner;

import org.example.utdemo.domain.BusinessObject;
import org.example.utdemo.event.GenericEvent;
import org.example.utdemo.listner.service.BusinessObjectListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessObjectListener extends AbstractGenericEventListener<BusinessObject> {
    Logger LOGGER = LoggerFactory.getLogger(BusinessObjectListener.class);
    @Autowired
    private BusinessObjectListenerService businessObjectListenerService;
    
    @Override
    protected Class<?> getEventType() {
        return BusinessObject.class;
    }
    
    @Override
    public void handleEvent(GenericEvent<BusinessObject> event) {
        businessObjectListenerService.handleEvent(event);
    }
}
