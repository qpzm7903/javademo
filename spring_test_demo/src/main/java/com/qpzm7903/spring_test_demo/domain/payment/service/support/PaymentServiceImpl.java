package com.qpzm7903.spring_test_demo.domain.payment.service.support;

import com.qpzm7903.spring_test_demo.domain.account.Account;
import com.qpzm7903.spring_test_demo.domain.payment.Payment;
import com.qpzm7903.spring_test_demo.domain.payment.service.PaymentService;
import com.qpzm7903.spring_test_demo.domain.user.User;
import com.qpzm7903.spring_test_demo.domain.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:19
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private final UserService userService;

    public PaymentServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Payment getById(Long id) {
        return null;
    }

    @Override
    public boolean pay(Long userId, Integer money) {
        User user = userService.getById(userId);
        Account account = user.getAccount();
        account.deduct(money);
        return true;
    }
}
