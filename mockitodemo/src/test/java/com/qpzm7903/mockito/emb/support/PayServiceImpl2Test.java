package com.qpzm7903.mockito.emb.support;

import com.qpzm7903.mockito.demo.User;
import com.qpzm7903.mockito.emb.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayServiceImpl2Test {
    
    @Mock
    private UserService userService;
    @InjectMocks
    PayServiceImpl payService;
    
    @Test
    void pay() {
        User value = User.builder()
                .balance(2)
                .build();
        when(userService.getUserById("userId")).thenReturn(value);
        int balance = payService.pay("userId", 1);
        Assertions.assertEquals(1, balance);
    }
    
    
}