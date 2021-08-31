package com.qpzm7903.mockito.demo;

import lombok.Builder;
import lombok.Data;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-04 08:27
 */

@Builder
@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String telephoneNumber;
    private String address;
    private String image;
    private String account;
    private int balance;
}
