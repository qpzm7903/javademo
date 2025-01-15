package org.example.utdemo.listner.service;

import org.example.utdemo.domain.PersistObject;
import org.example.utdemo.event.GenericEvent;

public interface PersisObjectListenerService {
    void handleEvent(GenericEvent<PersistObject> event);
}
