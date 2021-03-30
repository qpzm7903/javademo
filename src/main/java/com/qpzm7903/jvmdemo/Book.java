package com.qpzm7903.jvmdemo;

public class Book {
    private String name;
    private Author author;

    Book() {
        name = "";
        author = new Author();
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public static void main(String[] args) {
        Book book = new Book();
        ClassLoader classLoader = book.getClass().getClassLoader();
        System.out.println(classLoader);
        ClassLoader classLoader1 = book.getAuthor().getClass().getClassLoader();
        System.out.println(classLoader1);
    }
}
