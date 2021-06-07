package com.qpzm7903.behavioural_patterns.composite.employee.support;

import com.qpzm7903.behavioural_patterns.composite.employee.EmployeeRepo;

import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-08 07:52
 */

public class EmployeeRepoImpl implements EmployeeRepo {
    @Override
    public List<Long> getDepartmentEmployeeIds(long id) {
        return null;
    }

    @Override
    public double getEmployeeSalary(Long employeeId) {
        return 0;
    }
}
