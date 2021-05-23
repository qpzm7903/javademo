package com.example.spring_security_user_jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-22 22:04
 */

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

//    @Bean
//    DataSource dataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.h2.Driver");
//        dataSourceBuilder.url("jdbc:h2:mem:test");
//        dataSourceBuilder.username("SA");
//        dataSourceBuilder.password("");
//            return dataSourceBuilder.build();1
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 显式的表单登录
        http.formLogin();


        http.authorizeRequests()
                .antMatchers("/h2-console", "/h2-console/**").permitAll()
                .antMatchers("/boss/*").hasRole("boss")
                .antMatchers("/employee/*").hasAnyRole("boss", "employee")
                .antMatchers("/").permitAll();

        // 以下两行可以让h2能登录 or 通过 configure(WebSecurity web) 搞定
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("test")
                .password(passwordEncoder.encode("pass"))
                .roles("boss", "employee")
                .and()
                .withUser("test2")
                .password(passwordEncoder.encode("pass"))
                .roles("employee")
                .and()
                .passwordEncoder(passwordEncoder);

        // 使用这种方式，就需要按照默认的用户、权限表建立表结构，否则会查询不到，会触发sql错误
        /**
         * public static final String DEF_USERS_BY_USERNAME_QUERY =
         * "select username,password,enabled " +
         * "from users " +
         * "where username = ?";
         * public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
         * "select username,authority " +
         * "from authorities " +
         * "where username = ?";
         * public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
         * "select g.id, g.group_name, ga.authority " +
         * "from groups g, group_members gm, group_authorities ga " +
         * "where gm.username = ? " +
         * "and g.id = ga.group_id " +
         * "and g.id = gm.group_id";
         */
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder);

        // 也可以自定义查询
    }

    /**
     * 自定义权限查询
     *
     * @param auth
     * @throws Exception
     */
    void customJdbcAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username,password,enable from user where username=?")
                .authoritiesByUsernameQuery("select username,authrority from authority where user_id = ?");
    }

}
