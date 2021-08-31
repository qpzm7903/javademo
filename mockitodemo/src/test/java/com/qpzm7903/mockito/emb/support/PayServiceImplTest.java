package com.qpzm7903.mockito.emb.support;

import com.qpzm7903.mockito.demo.User;
import com.qpzm7903.mockito.emb.UserRepo;
import com.qpzm7903.mockito.emb.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-31-22:50
 */
@ExtendWith(MockitoExtension.class)
class PayServiceImplTest {

    @InjectMocks
    PayServiceImpl payService;

    @Mock
    UserService userService;

    @Mock
    UserRepo userRepo;

    /**
     * 三个类的依赖关系是
     * payService
     * |----userService
     *      |----userRepo
     *
     * 使用mockito的自动注入，那么结果就是
     * payServiceImpl---真实对象
     * |---- userService---mock对象
     *       |---- userRepo --- null
     */
    @Test
    void test() {
        when(userService.getUserById("1")).thenReturn(User.builder().balance(100).build());
        int pay = payService.pay("1", 1);
        Assertions.assertEquals(99, pay);
    }

    /**
     * 通过手动设置的方式，可以自由的注入
     */
    @Test
    void test_mock_by_hand() {

    }

}