package com.qpzm7903.structural_patterns.proxyPattern;

public interface IUserController {
    UserVo login(String userName, String password);
    UserVo register(String userName, String password);
}
