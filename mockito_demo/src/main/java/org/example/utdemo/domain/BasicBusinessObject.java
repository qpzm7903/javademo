package org.example.utdemo.domain;

public class BasicBusinessObject extends BasicObject implements BusinessObject {
    protected String name;
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
