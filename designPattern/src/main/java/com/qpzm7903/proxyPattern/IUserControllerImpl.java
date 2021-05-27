package com.qpzm7903.proxyPattern;

/**
 * @author qpzm7903
 * @since 2020-06-11-21:42
 */

public class IUserControllerImpl implements IUserController {
    @Override
    public UserVo login(String userName, String password) {
        System.out.println(userName + "login");
        return new UserVo(userName, password);
    }

    @Override
    public UserVo register(String userName, String password) {
        System.out.println(userName + "register");
        return new UserVo(userName, password);
    }
}
