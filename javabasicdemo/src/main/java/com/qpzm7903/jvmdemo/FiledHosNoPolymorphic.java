package com.qpzm7903.jvmdemo;

public class FiledHosNoPolymorphic {

    static class Father {
        public int money = 1;
        public Father(){
            money = 2;
            showMeTheMoney();
        }

        public void showMeTheMoney() {
            System.out.println("I am father, i have $" + money);
        }
    }

    static class Son extends Father {
        public int money = 3;
        public Son(){
            money = 4;
            showMeTheMoney();
        }
        public void showMeTheMoney() {
            System.out.println("I am Son, i have $" + money);
        }
    }

    public static void main(String[] args) {
        Father gay = new Son();
        System.out.println("this gay has $" + gay.money);
//        I am Son, i have $0   隐士调用父类的构造器，然后调用showMeTheMoney，进行动态分派，实际类型时Son，所以调用了Son的方法，但是在Son的方法里面，遇到了同名的filed，son的还没初始化，但是已加载，所以时0
//        I am Son, i have $4
//        this gay has $2  // 通过静态类型访问，所以访问到了父类的filed
    }

}
