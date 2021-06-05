package com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 21:12
 */

public class HandlerChain {
    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }

    public boolean handler(Message message) {
        for (Handler handler : handlers) {
            if (handler.handle(message)) {
                return true;
            }
        }
        return false;
    }
}
