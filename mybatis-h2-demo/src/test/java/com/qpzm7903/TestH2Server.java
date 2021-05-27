package com.qpzm7903;

import org.junit.jupiter.api.Test;
import  org.h2.tools.Server;

import java.sql.SQLException;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-28 06:30
 */

public class TestH2Server {

    @Test
    void test() throws SQLException {
        Server server = Server.createTcpServer().start();


    }


}
