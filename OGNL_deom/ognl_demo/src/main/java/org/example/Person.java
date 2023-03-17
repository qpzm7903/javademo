package org.example;

import ognl.*;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.Map;

public class Person {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws OgnlException {
        // 构建一个OgnlContext对象
        Person person = new Person();
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(person,
                new MyMemberAccess());


        person.setName("Alice");
        String name = (String) Ognl.getValue("name", context, person);
        System.out.println("Name: " + name); // Output: Name: Alice

        person.setAge(30);
        int age = (Integer) Ognl.getValue("age", context, person);
        System.out.println("Age: " + age); // Output: Age: 30

    }

    public static class MyMemberAccess implements MemberAccess {

        @Override
        public Object setup(Map map, Object o, Member member, String s) {
            return null;
        }

        @Override
        public void restore(Map map, Object o, Member member, String s, Object o1) {

        }

        @Override
        public boolean isAccessible(Map map, Object o, Member member, String s) {
            return true;
        }
    }

}
