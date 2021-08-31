package com.qpzm7903.spring_test_demo.domain.user.service.repo.support;

import com.qpzm7903.spring_test_demo.domain.user.User;
import com.qpzm7903.spring_test_demo.domain.user.service.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:27
 */
@Service
public class UserRepoImpl implements UserRepo {
    @Override
    public User getById(Long id) {
        return null;
    }
}
