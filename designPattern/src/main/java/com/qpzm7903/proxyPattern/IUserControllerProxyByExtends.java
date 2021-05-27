package com.qpzm7903.proxyPattern;

/**
 * @author qpzm7903
 * @since 2020-06-11-21:51
 */

public class IUserControllerProxyByExtends extends IUserControllerImpl {
    @Override
    public UserVo login(String userName, String password) {
        // do something before login
        UserVo user = super.login(userName, password);
        // do something after login
        return user;
    }

    @Override
    public UserVo register(String userName, String password) {
        // do something before register
        UserVo user = super.register(userName, password);
        // do something after register
        return user;
    }
}
