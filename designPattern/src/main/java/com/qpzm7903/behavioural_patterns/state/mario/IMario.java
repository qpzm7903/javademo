package com.qpzm7903.behavioural_patterns.state.mario;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-16 20:59
 */

public interface IMario {
    State getName();

    // 定义事件
    void obtainMushRoom();

    void obtainCape();

    void obtainFireFlower();

    void meetMonster();
}
