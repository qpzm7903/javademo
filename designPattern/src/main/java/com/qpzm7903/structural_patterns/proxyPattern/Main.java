package com.qpzm7903.structural_patterns.proxyPattern;

/**
 * @author qpzm7903
 * @since 2020-06-11-21:46
 */

public class Main {
    public static void main(String[] args) {
        IUserController userControllerByImpl = new IUserControllerProxy(new IUserControllerImpl());

        IUserController userControllerByExtends = new IUserControllerProxyByExtends();
    }
}
