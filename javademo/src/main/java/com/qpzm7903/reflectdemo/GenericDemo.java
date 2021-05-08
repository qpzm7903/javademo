package com.qpzm7903.reflectdemo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qpzm7903
 * @since 2020-06-13-21:55
 */

public class GenericDemo {

    public static void main(String[] args) {

        List<GenericDemo> list = new LinkedList<GenericDemo>();
        Class<? extends List> aClass = list.getClass();
        TypeVariable<? extends Class<? extends List>>[] typeParameters = aClass.getTypeParameters();
        System.out.println(typeParameters.length);
        ;
        for (TypeVariable<? extends Class<? extends List>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getGenericDeclaration());
            System.out.println(typeParameter.getTypeName());
        }


        Relation<Source, Target> relation = new Relation<>();
        Class<? extends Relation> aClass1 = relation.getClass();
        TypeVariable<? extends Class<? extends Relation>>[] typeParameters1 = aClass1.getTypeParameters();
        for (TypeVariable<? extends Class<? extends Relation>> typeVariable : typeParameters1) {
            System.out.println(typeVariable.getName());
            System.out.println(typeVariable.getTypeName());
        }

        Type genericSuperclass = aClass1.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument);
        }


    }

    static class Relation<S extends Object, T extends Object> {
        S s;

        T t;

        public Class<T> getTClass() {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return tClass;
        }

        public Class<S> getSClass() {
            Class<S> sClass = (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return sClass;
        }


    }

    class Source {
    }

    class Target {
    }


}
