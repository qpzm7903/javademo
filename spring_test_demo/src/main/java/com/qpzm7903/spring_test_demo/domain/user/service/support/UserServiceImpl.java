package com.qpzm7903.spring_test_demo.domain.user.service.support;

import com.qpzm7903.spring_test_demo.domain.account.service.AccountService;
import com.qpzm7903.spring_test_demo.domain.user.User;
import com.qpzm7903.spring_test_demo.domain.user.service.UserService;
import com.qpzm7903.spring_test_demo.domain.user.service.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:19
 */
@Service
public class UserServiceImpl implements UserService {
    private final AccountService accountService;
    private final UserRepo userRepo;

    public UserServiceImpl(AccountService accountService, UserRepo userRepo) {
        this.accountService = accountService;
        this.userRepo = userRepo;
    }

    @Override
    public User getById(Long id) {
        return userRepo.getById(id);
    }
}
