package com.example.h2demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;

import java.util.List;

@SpringBootTest
class UserControllerTest {


    @Autowired
    private UserController userController;

    @Autowired
    private UserRepo userRepo;

    @Test
    void test_context_load() {
        assert userController != null;
    }

    @Test
    void test_user_list() {
        List<User> users = userController.users();
        assert users.size() == 0;
    }

    @Test
    void test_save_and_query() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        User user1 = userController.createUser(user);
        User userByUserId = userController.getUserByUserId(user1.getId());

        Assertions.assertEquals(user1.getUsername(), userByUserId.getUsername());
        Assertions.assertEquals(user1.getPassword(), userByUserId.getPassword());

        userRepo.deleteById(user1.getId());

    }
}