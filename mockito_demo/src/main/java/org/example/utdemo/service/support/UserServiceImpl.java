package org.example.utdemo.service.support;

import org.example.utdemo.domain.User;
import org.example.utdemo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Override
    public List<User> getUserById(String id) {
        return List.of();
    }
}
