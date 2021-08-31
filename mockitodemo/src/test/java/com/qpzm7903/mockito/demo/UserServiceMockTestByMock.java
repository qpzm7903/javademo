package com.qpzm7903.mockito.demo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

class UserServiceMockTestByMock {

    @Mock
    private BlogService blogService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(UserServiceMockTestByMock.class);

    }

    @Test
    void assertMock(@Mock BlogService blogService) {
        Assertions.assertTrue(Objects.nonNull(blogService));
    }

    @Test
    void test_create_user() {

    }

    @Test
    void test_delete_user() {

    }

    @Test
    void test_update_usr() {

    }

    @Test
    void test_query_user() {

    }

}