package com.qpzm7903.day2.model;

public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    public User(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }
} 