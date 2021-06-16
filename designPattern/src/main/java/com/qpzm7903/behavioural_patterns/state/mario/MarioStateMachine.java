package com.qpzm7903.behavioural_patterns.state.mario;

import com.qpzm7903.behavioural_patterns.state.mario.support.SmallMario;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-16 21:07
 */

public class MarioStateMachine {
    private int score;
    private IMario currentState;

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = new SmallMario(this);
    }

    public void obtainMushRoom() {
        this.currentState.obtainMushRoom();
    }

    public void obtainCape() {
        this.currentState.obtainCape();
    }

    public void obtainFireFlower() {
        this.currentState.obtainFireFlower();
    }

    public void meetMonster() {
        this.currentState.meetMonster();
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public IMario getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(IMario currentState) {
        this.currentState = currentState;
    }
}
