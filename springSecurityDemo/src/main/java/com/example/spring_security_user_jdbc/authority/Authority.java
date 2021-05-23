package com.example.spring_security_user_jdbc.authority;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-23 07:50
 */
@Table(name = "authorities")
@Entity
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_id")
    Long userId;

    String authority;

    String username;
}
