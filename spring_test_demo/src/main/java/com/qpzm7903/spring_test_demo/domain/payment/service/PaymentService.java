package com.qpzm7903.spring_test_demo.domain.payment.service;

import com.qpzm7903.spring_test_demo.domain.payment.Payment;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:19
 */
public interface PaymentService {
    Payment getById(Long id);

    boolean pay(Long userId, Integer money);
}
