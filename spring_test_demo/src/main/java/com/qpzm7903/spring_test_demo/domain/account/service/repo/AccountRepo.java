package com.qpzm7903.spring_test_demo.domain.account.service.repo;

import com.qpzm7903.spring_test_demo.domain.account.Account;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:30
 */
public interface AccountRepo {
    Account getById(Long id);
}
