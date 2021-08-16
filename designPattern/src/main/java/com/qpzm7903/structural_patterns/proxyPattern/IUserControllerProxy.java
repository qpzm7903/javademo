package com.qpzm7903.structural_patterns.proxyPattern;

/**
 * 通过实现接口，组合的方式代理
 *
 * @author qpzm7903
 * @since 2020-06-11-21:44
 */

public class IUserControllerProxy implements IUserController {

    private IUserController userController;

    public IUserControllerProxy(IUserController userController) {
        this.userController = userController;
    }


    @Override

    public UserVo login(String userName, String password) {
        // do something before login
        UserVo userVo = userController.login(userName, password);
        // do something after login
        return userVo;
    }

    @Override
    public UserVo register(String userName, String password) {
        // do something before register
        UserVo userVo = userController.register(userName, password);
        // do something after register
        return userVo;
    }
}
