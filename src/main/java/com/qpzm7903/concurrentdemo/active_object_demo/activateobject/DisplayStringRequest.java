package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;


/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 06:21
 */

public class DisplayStringRequest extends MethodRequest<Object> {
    private final String string;

    public DisplayStringRequest(Servant servant, String string) {
        super(servant, null);
        this.string = string;
    }

    @Override
    public void execute() {
        servant.displayString(string);
    }
}
