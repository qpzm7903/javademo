package com.qpzm.test;

import com.qpzm.anno.Data;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-12-22:19
 */
@Data
public class User {
    private int id;
    private String name;

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setName("qpzm");
        System.out.println("Id"+user.getId() + " name:" + user.getName());
    }
}
