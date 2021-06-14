package com.qpzm7903.genericDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-13 21:01
 */

public class App {

    public static void main(String[] args) {
        List<Fruit> fruitList = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();

        fruitList.add(new Fruit());
        fruitList.add(new Apple());

        Fruit fruit = fruitList.get(0);
        Fruit fruit1 = fruitList.get(1); // 拿出来都是fruit

        apples.add(new Apple());
//        apples.add(new Fruit());  // 编译器报错


        // upper bounds wildcards

    }

    ;

    void upper_bounds_wildcards_demo() {
        // 一个能放水果、以及一切水果派生类的容器
        // List<? extends Fruit> 是  ArrayList<Apple> 的父类
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple());
        List<Food> foods = new ArrayList<>();

        List<? extends Fruit> fruits = apples;
//        fruits = foods; // 编译报错 Required type: List <? extends Fruit> Provided: List <Food>
        // 只能取，不能存
//        fruits.add(new Apple()); // 编译报错 Required type: capture of ? extends Fruit Provided: Apple
//        fruits.add(new Fruit()); // 编译报错;
//        fruits.add(new Food()); // 编译报错
//        fruits.add(new Object()); // 编译报错;

        // 拿出来都是水果，但是不直到是什么水果，
        Fruit fruit = fruits.get(0);

    }

    ;

    void lower_bounds_wildcards() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit());

        List<Food> foods = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();
        List<Object> objects = new ArrayList<>();


        // 一个能放水果，以及一切是水果的基类的容器
        // List<? super Fruit> 是 List<Fruit> 的基类
        // List<? super Fruit> 是 List<Food> 的基类
        //  List<? super Fruit> 是 List<Object>
        // List<? super Fruit> 限定了类型是Fruit的基类
        List<? super Fruit> fruit = fruits;
        fruit = objects;
        fruit = foods;
//        fruit = apples;// 编译报错 Required type: List <? super Fruit> Provided: List <Apple>

        // add操作部分失效
        fruit.add(new Fruit());
        fruit.add(new Apple());
        fruit.add(new RedApple());
//        fruit.add(new Food()); // 编译报错 Required type: capture of ? super Fruit  Provided: Food
//        fruit.add(new Object()); // 编译报错 Required type: capture of ? super Fruit Provided: Object

        // get操作都变成了object，因为都不知道类型
        Object object = fruit.get(0);

    }

    ;

    static class Food {
    }

    ;

    static class Meat extends Food {
    }

    ;

    static class Fruit extends Food {
    }

    ;

    static class Apple extends Fruit {
    }

    ;

    static class Banana extends Fruit {
    }

    ;

    static class Pork extends Meat {
    }

    ;

    static class Beef extends Meat {
    }

    static class RedApple extends Apple {
    }

    static class GreenApple extends Apple {
    }
}
