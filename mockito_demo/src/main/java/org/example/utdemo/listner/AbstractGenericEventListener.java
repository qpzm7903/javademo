package org.example.utdemo.listner;

import java.util.concurrent.ExecutorService;

public abstract class AbstractGenericEventListener<T> implements GenericEventListener<T> {
    @Override
    public boolean isAsync() {
        return false; // 默认同步处理，子类可重写
    }
    
    @Override
    public ExecutorService getThreadPool() {
        return null; // 默认不使用自定义线程池，子类可重写
    }
    
    @Override
    public boolean supportsEventType(Class<?> eventType) {
        return getEventType().isAssignableFrom(eventType);
    }
    
    protected abstract Class<?> getEventType();
}
