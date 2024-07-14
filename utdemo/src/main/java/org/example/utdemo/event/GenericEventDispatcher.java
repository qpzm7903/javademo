package org.example.utdemo.event;


import org.example.utdemo.listner.GenericEventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenericEventDispatcher {
    private final Map<Class<?>, List<GenericEventListener<?>>> listeners = new HashMap<>();

    public <T> void registerListener(GenericEventListener<T> listener) {
        List<Class<?>> eventTypes = getEventTypes(listener);
        for (Class<?> eventType : eventTypes) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        }
    }

    private List<Class<?>> getEventTypes(GenericEventListener<?> listener) {
        List<Class<?>> eventTypes = new ArrayList<>();
        for (Type type : listener.getClass().getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                if (pt.getRawType().equals(GenericEventListener.class)) {
                    eventTypes.add((Class<?>) pt.getActualTypeArguments()[0]);
                }
            }
        }
        return eventTypes;
    }

    @SuppressWarnings("unchecked")
    public <T> void dispatch(GenericEvent<T> event) {
        Class<?> eventType = event.getData().getClass();
        for (Map.Entry<Class<?>, List<GenericEventListener<?>>> entry : listeners.entrySet()) {
            if (entry.getKey().isAssignableFrom(eventType)) {
                for (GenericEventListener<?> listener : entry.getValue()) {
                    if (listener.supportsEventType(eventType)) {
                        ((GenericEventListener<T>) listener).handleEvent(event);
                    }
                }
            }
        }
    }
}
