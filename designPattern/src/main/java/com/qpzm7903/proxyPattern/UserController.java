package com.qpzm7903.proxyPattern;

/**
 * @author qpzm7903
 * @since 2020-06-12-7:06
 */

public class UserController {
    public UserVo login(String userName, String password) {
        System.out.println(userName + "login");
        return new UserVo(userName, password);
    }

    public UserVo register(String userName, String password) {
        System.out.println(userName + "register");
        return new UserVo(userName, password);
    }
}
