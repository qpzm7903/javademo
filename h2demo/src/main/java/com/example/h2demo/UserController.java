package com.example.h2demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-22 11:31
 */

@RestController
public class UserController {

    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(path = "/user/list")
    public List<User> users() {
        Iterable<User> all = userRepo.findAll();
        List<User> result = new ArrayList<>();
        all.forEach(result::add);

        return result;
    }

    @PostMapping(path = "/user")
    public User createUser(@RequestBody User user) {
        userRepo.save(user);
        return user;
    }

    @GetMapping(path = "/user/{userId}")
    public User getUserByUserId(@PathVariable(name = "userId") Long userId) {
        Optional<User> byId = userRepo.findById(userId);
        return byId.orElse(new User());
    }


}
