package com.qpzm7903.mockito.emb.support;

import com.qpzm7903.mockito.demo.User;
import com.qpzm7903.mockito.emb.UserRepo;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-31-22:44
 */
public class UserRepoImpl implements UserRepo {
    @Override
    public User getUserById(String id) {
        return User.builder().build();
    }
}
