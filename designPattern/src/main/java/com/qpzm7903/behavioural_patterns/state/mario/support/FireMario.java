package com.qpzm7903.behavioural_patterns.state.mario.support;

import com.qpzm7903.behavioural_patterns.state.mario.IMario;
import com.qpzm7903.behavioural_patterns.state.mario.MarioStateMachine;
import com.qpzm7903.behavioural_patterns.state.mario.State;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-16 21:06
 */

public class FireMario implements IMario {

    private final MarioStateMachine stateMachine;

    public FireMario(MarioStateMachine marioStateMachine) {
        this.stateMachine = marioStateMachine;
    }

    @Override
    public State getName() {
        return State.FIRE;
    }

    @Override
    public void obtainMushRoom() {
        // do nothing
    }

    @Override
    public void obtainCape() {
        // do nothing
    }

    @Override
    public void obtainFireFlower() {
        // do nothing
    }

    @Override
    public void meetMonster() {
        stateMachine.setCurrentState(new SmallMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() - 300);
    }
}
