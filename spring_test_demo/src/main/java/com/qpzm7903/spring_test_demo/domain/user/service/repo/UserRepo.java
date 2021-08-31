package com.qpzm7903.spring_test_demo.domain.user.service.repo;

import com.qpzm7903.spring_test_demo.domain.user.User;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:27
 */
public interface UserRepo {
    User getById(Long id);
}
