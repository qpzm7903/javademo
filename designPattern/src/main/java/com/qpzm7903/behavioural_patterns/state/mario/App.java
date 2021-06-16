package com.qpzm7903.behavioural_patterns.state.mario;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-16 21:23
 */

public class App {
    public static void main(String[] args) {
        MarioStateMachine marioStateMachine = new MarioStateMachine();
        marioStateMachine.obtainMushRoom();
        marioStateMachine.obtainCape();
        marioStateMachine.meetMonster();
        System.out.println(marioStateMachine.getCurrentState());
        System.out.println(marioStateMachine.getScore());
    }
}
