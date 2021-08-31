package com.qpzm7903.mockito.emb.support;

import com.qpzm7903.mockito.demo.User;
import com.qpzm7903.mockito.emb.UserRepo;
import com.qpzm7903.mockito.emb.UserService;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-31-22:43
 */
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    @Override
    public User getUserById(String id) {
        return userRepo.getUserById(id);
    }
}
