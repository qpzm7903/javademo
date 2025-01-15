package com.example.spring_security_user_jdbc.authority;

import org.springframework.data.repository.CrudRepository;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-23 07:53
 */

public interface AuthorityRepo extends CrudRepository<Authority, Long> {
}
