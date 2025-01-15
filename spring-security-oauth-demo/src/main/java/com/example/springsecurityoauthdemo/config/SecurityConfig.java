package com.example.springsecurityoauthdemo.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-07-07 20:18
 */

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig {
    
    // WebSecurityConfigurerAdapter
    // @Bean
    // @Override
    // public AuthenticationManager authenticationManagerBean() throws Exception {
    //     return super.authenticationManagerBean();
    // }
    //
    // @Bean
    // @Override
    // public UserDetailsService userDetailsServiceBean() throws Exception {
    //     return super.userDetailsServiceBean();
    // }
    //
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     super.configure(auth);
    //     auth.inMemoryAuthentication().withUser("qpzm7903").password("{noop}test").roles("ADMIN");
    // }
}
