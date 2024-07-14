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
    
    @Test
    void testGetUsers() {
        User e1 = new User();
        e1.setName("test");
        Mockito.when(userService.getUserById(Mockito.anyString()))
                .thenReturn(List.of(e1));
        List<User> testId = applicationService.listAllUsersOfApp("testId");
        Assertions.assertFalse(testId.isEmpty());
        Assertions.assertEquals(testId.size(), 1);
        Assertions.assertEquals(testId.get(0)
                .getName(), "test");
        
    }
}