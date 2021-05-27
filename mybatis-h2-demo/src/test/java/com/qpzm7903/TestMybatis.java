package com.qpzm7903;

import com.qpzm7903.mybatis.demo.Blog;
import com.qpzm7903.mybatis.demo.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-27 21:40
 */

public class TestMybatis {

    static String readFile(String filePath) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1) {
            stringBuilder.append((char) c);
        }
        return stringBuilder.toString();

    }

    @BeforeAll
    static void init() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "root", "");
        Statement statement = connection.createStatement();
        String sql = readFile("db/schema.sql");
        System.out.println(sql);
        statement.execute(sql);
        statement.close();
        connection.close();
    }

    @Test
    void test_init() throws IOException {
        String resouce = "mybatis/config/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = new Blog();
            blog.setId(1);
            blog.setContent("testContent");
            blog.setTitle("Test title");

            mapper.save(blog);

            Blog blog1 = mapper.get(1);

            Assertions.assertEquals(blog.getContent(), blog1.getContent());
            Assertions.assertEquals(blog.getTitle(), blog1.getTitle());
        }
    }
}
