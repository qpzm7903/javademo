package com.qpzm7903.mockito.emb.support;

import com.qpzm7903.mockito.demo.User;
import com.qpzm7903.mockito.emb.PayService;
import com.qpzm7903.mockito.emb.UserService;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-31-22:42
 */
public class PayServiceImpl implements PayService {

    private UserService userService;

    @Override
    public int pay(String userId, int count) {
        User user = userService.getUserById(userId);
        user.setBalance(user.getBalance() - count);
        return user.getBalance();
    }
}
