package com.example.spring_security_user_jdbc.user;

import jakarta.persistence.*;
import lombok.Data;


/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-22 11:29
 */

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    String username;

    String password;

    Boolean enabled;
}
