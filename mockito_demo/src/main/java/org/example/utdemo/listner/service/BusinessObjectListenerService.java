package org.example.utdemo.listner.service;

import org.example.utdemo.domain.BusinessObject;
import org.example.utdemo.domain.PersistObject;
import org.example.utdemo.event.GenericEvent;

public interface BusinessObjectListenerService {
    void handleEvent(GenericEvent<BusinessObject> event);
}
