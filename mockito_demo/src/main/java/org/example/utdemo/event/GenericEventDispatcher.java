package org.example.utdemo.event;


import org.example.utdemo.listner.AbstractGenericEventListener;
import org.example.utdemo.listner.GenericEventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GenericEventDispatcher {
    private final Map<Class<?>, List<GenericEventListener<?>>> listeners = new HashMap<>();
    private final ExecutorService defaultThreadPool = Executors.newCachedThreadPool();
    
    public GenericEventDispatcher(List<GenericEventListener<?>> registeredListeners) {
        registeredListeners.forEach(this::registerListener);
    }
    
    public <T> void registerListener(GenericEventListener<T> listener) {
        List<Class<?>> eventTypes = getEventTypes(listener);
        for (Class<?> eventType : eventTypes) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add(listener);
        }
    }
    
    private List<Class<?>> getEventTypes(GenericEventListener<?> listener) {
        List<Class<?>> eventTypes = new ArrayList<>();
        Type genericSuperclass = listener.getClass()
                .getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType pt) {
            if (pt.getRawType()
                    .equals(AbstractGenericEventListener.class)) {
                eventTypes.add((Class<?>) pt.getActualTypeArguments()[0]);
            }
        }
        return eventTypes;
    }
    
    @SuppressWarnings("unchecked")
    public <T> void dispatch(GenericEvent<T> event) {
        Class<?> eventType = event.getData()
                .getClass();
        for (Map.Entry<Class<?>, List<GenericEventListener<?>>> entry : listeners.entrySet()) {
            if (!entry.getKey()
                    .isAssignableFrom(eventType)) {
                continue;
            }
            for (GenericEventListener<?> listener : entry.getValue()) {
                if (!listener.supportsEventType(eventType)) {
                    continue;
                }
                if (listener.isAsync()) {
                    ExecutorService threadPool = listener.getThreadPool();
                    if (threadPool == null) {
                        threadPool = defaultThreadPool;
                    }
                    threadPool.submit(() -> ((GenericEventListener<T>) listener).handleEvent(event));
                } else {
                    ((GenericEventListener<T>) listener).handleEvent(event);
                }
            }
        }
    }
}
