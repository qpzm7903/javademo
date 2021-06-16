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

public class SmallMario implements IMario {

    private final MarioStateMachine stateMachine;

    public SmallMario(MarioStateMachine marioStateMachine) {
        this.stateMachine = marioStateMachine;
    }

    @Override
    public State getName() {
        return State.SMALL;
    }

    @Override
    public void obtainMushRoom() {
        stateMachine.setCurrentState(new SupperMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() + 100);
    }

    @Override
    public void obtainCape() {
        stateMachine.setCurrentState(new CapeMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() + 200);
    }

    @Override
    public void obtainFireFlower() {
        stateMachine.setCurrentState(new FireMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() + 300);
    }

    @Override
    public void meetMonster() {
        // small mario will die when meet monster
    }
}
