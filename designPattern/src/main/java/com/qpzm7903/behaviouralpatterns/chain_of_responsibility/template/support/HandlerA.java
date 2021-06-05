package com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template.support;

import com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template.Handler;
import com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template.Message;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 21:07
 */

public class HandlerA implements Handler {

    @Override
    public boolean handle(Message message) {
        if (message.getCode() == 0 && message.getMessage().contains("A")) {
            System.out.println("handler A handle message:" + message);

            return true;
        }
        return false;
    }
}
