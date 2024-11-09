package com.example.transaction_demo.domain.model;

import lombok.Data;

@Data
public class Member {
    private String id;
    private User user;
    private int points;
}
