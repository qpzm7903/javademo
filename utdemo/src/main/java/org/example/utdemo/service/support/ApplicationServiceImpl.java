package org.example.utdemo.service.support;

import org.example.utdemo.domain.User;
import org.example.utdemo.service.ApplicationService;
import org.example.utdemo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private UserService userService;
    
    public ApplicationServiceImpl(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public List<User> listAllUsersOfApp(String appId) {
        return userService.getUserById(appId);
    }
}
