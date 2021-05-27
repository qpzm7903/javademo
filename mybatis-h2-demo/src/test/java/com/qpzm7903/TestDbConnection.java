package com.qpzm7903;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * reference https://www.baeldung.com/java-jdbc
 *
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-26 08:22
 */

public class TestDbConnection {
    public class Employee {
        private int id;
        private String name;
        private String position;
        private double salary;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", position='" + position + '\'' +
                    ", salary=" + salary +
                    '}';
        }
        // standard constructor, getters, setters
    }

    @Test
    void test_jdbc_connection_and_h2() throws ClassNotFoundException, SQLException {
        // registry driver
        registryDriver();

        // get connection
        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "root", "")) {
            // get statement
            try (Statement stmt = con.createStatement()) {
                insert(stmt);
                select(stmt);
                update(con);
            }
        }

    }

    private void registryDriver() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    private void execute(Connection con, Statement stmt, String sql) throws SQLException {
        stmt.execute(sql);
    }

    private void update(Connection con) throws SQLException {
        String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(updatePositionSql)) {
            // use pstmt here
            pstmt.setString(1, "lead developer");
            pstmt.setInt(2, 1);
            int rowsAffected = pstmt.executeUpdate();
            Assertions.assertEquals(1, rowsAffected);
        }
    }

    private void select(Statement stmt) throws SQLException {
        String selectSql = "SELECT * FROM employees";
        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            // use resultSet here
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setId(resultSet.getInt("emp_id"));
                emp.setName(resultSet.getString("name"));
                emp.setPosition(resultSet.getString("position"));
                emp.setSalary(resultSet.getDouble("salary"));
                employees.add(emp);
            }
            employees.forEach(System.out::println);
        }
    }

    private void insert(Statement stmt) throws SQLException {
        String tableSql = "CREATE TABLE IF NOT EXISTS employees"
                + "(emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
                + "position varchar(30), salary double)";
        stmt.execute(tableSql);
        String insertSql = "INSERT INTO employees(name, position, salary)"
                + " VALUES('john', 'developer', 2000)";
        stmt.executeUpdate(insertSql);
    }
}
