package com.qpzm7903.spring_test_demo.domain.account;

import lombok.Data;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:02
 */
@Data
public class Account {
    private Long Id;
    private Long balance;

    public void deduct(Integer money) {
        this.balance = this.balance - money;
    }
}
