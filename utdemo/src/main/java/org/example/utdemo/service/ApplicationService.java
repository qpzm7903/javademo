package org.example.utdemo.service;

import org.example.utdemo.domain.User;

import java.util.List;

public interface ApplicationService {
    List<User> listAllUsersOfApp(String appId);
}
