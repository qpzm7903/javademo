package com.qpzm7903.mybatis.demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-26 07:49
 */

public interface BlogMapper {

    Blog get(Integer id);

    void save(Blog blog);

}
