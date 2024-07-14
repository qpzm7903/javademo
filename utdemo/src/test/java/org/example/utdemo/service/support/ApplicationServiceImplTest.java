package org.example.utdemo.service.support;

import org.example.utdemo.domain.User;
import org.example.utdemo.listner.UserListener;
import org.example.utdemo.service.ApplicationService;
import org.example.utdemo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class ApplicationServiceImplTest {
    
    @Autowired
    ApplicationService applicationService;
    
    @MockBean
    UserService userService;

    @Autowired
    private UserListener testListener;
    
    @Test
    void testGetUsers() {
        List<User> testId = applicationService.listAllUsersOfApp("testId");
        Mockito.when(userService.getUserById(Mockito.anyString()))
                .thenReturn(List.of(new User()));
        Assertions.assertFalse(testId.isEmpty());
    }
}