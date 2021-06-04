package com.qpzm7903.mockito.demo;

import java.util.List;

public interface UserSservice {
    User getUserById(Long id);

    User createUser(User user);

    boolean deleteUser(User user);

    List<User> listUser();

    boolean checkUserExist(User user);
}
