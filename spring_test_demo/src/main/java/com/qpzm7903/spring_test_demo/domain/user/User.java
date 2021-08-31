package com.qpzm7903.spring_test_demo.domain.user;

import com.qpzm7903.spring_test_demo.domain.account.Account;
import lombok.Data;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-01-7:02
 */
@Data
public class User {
    private Long id;
    private Account account;

}
