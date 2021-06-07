package com.qpzm7903.behavioural_patterns.chain_of_responsibility.template.support;

import com.qpzm7903.behavioural_patterns.chain_of_responsibility.template.Handler;
import com.qpzm7903.behavioural_patterns.chain_of_responsibility.template.Message;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 21:07
 */

public class HandlerB implements Handler {
    @Override
    public boolean handle(Message message) {
        boolean a = message.getMessage().contains("B");
        if (a) {
            System.out.println("handler B handler message:" + message);
        }
        return message.getCode() == 0 && a;
    }
}
