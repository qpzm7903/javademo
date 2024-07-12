package org.example.utdemo.service.support;

import org.example.utdemo.domain.User;
import org.example.utdemo.service.ApplicationService;
import org.example.utdemo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationServiceImplTest {
    
    @Autowired
    ApplicationService applicationService;
    
    @Mock
    UserService userService;
    
    @Test
    void testGetUsers() {
        List<User> testId = applicationService.listAllUsersOfApp("testId");
        Mockito.when(userService.getUserById(Mockito.anyString()))
                .thenReturn(List.of(new User()));
        Assertions.assertTrue(testId.size() > 0);
    }
}