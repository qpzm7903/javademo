package com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template;

import com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template.support.HandlerA;
import com.qpzm7903.behaviouralpatterns.chain_of_responsibility.template.support.HandlerB;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-05 21:14
 */

public class App {
    public static void main(String[] args) {
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(new HandlerA());
        handlerChain.addHandler(new HandlerB());

        handlerChain.handler(new Message.DefaultMessage("A", 0));
        handlerChain.handler(new Message.DefaultMessage("B", 0));

    }
}
