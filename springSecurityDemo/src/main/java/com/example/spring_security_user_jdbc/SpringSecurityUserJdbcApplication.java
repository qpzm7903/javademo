package com.example.spring_security_user_jdbc;

import com.example.spring_security_user_jdbc.authority.Authority;
import com.example.spring_security_user_jdbc.authority.AuthorityRepo;
import com.example.spring_security_user_jdbc.user.User;
import com.example.spring_security_user_jdbc.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityUserJdbcApplication implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthorityRepo authorityRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityUserJdbcApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        User qpzm = new User();
        qpzm.setUsername("qpzm");
        qpzm.setPassword(passwordEncoder.encode("password"));
        qpzm.setEnabled(true);

        userRepo.save(qpzm);

        Authority authority = new Authority();
        authority.setAuthority("boss");
        authority.setUserId(qpzm.getId());
        authority.setUsername(qpzm.getUsername());
        authorityRepo.save(authority);
    }
}
