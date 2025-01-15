package org.example.utdemo.domain;


public class BasicObject implements PersistObject {
    protected String id;
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
}
