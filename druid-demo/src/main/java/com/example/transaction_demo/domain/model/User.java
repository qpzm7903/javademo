package com.example.transaction_demo.domain.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
