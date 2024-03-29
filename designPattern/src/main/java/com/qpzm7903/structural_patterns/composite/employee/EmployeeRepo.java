package com.qpzm7903.structural_patterns.composite.employee;

import java.util.List;

public interface EmployeeRepo {
    List<Long> getDepartmentEmployeeIds(long id);

    double getEmployeeSalary(Long employeeId);
}
