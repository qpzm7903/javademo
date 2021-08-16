package com.qpzm7903.structural_patterns.composite.employee;

import com.qpzm7903.structural_patterns.composite.employee.support.DepartmentRepoImpl;
import com.qpzm7903.structural_patterns.composite.employee.support.EmployeeRepoImpl;

import java.util.List;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-08 07:50
 */

public class App {
    private static final long ORGANIZATION_ROOT_ID = 1001;
    private final DepartmentRepo departmentRepo; // 依赖注入
    private final EmployeeRepo employeeRepo; // 依赖注入

    public App(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }

    public void buildOrganization() {
        Department rootDepartment = new Department(ORGANIZATION_ROOT_ID);
        buildOrganization(rootDepartment);
    }

    private void buildOrganization(Department department) {
        List<Long> subDepartmentIds = departmentRepo.getSubDepartmentIds(department.getId());
        for (Long subDepartmentId : subDepartmentIds) {
            Department subDepartment = new Department(subDepartmentId);
            department.addSubNode(subDepartment);
            buildOrganization(subDepartment);
        }
        List<Long> employeeIds = employeeRepo.getDepartmentEmployeeIds(department.getId());
        for (Long employeeId : employeeIds) {
            double salary = employeeRepo.getEmployeeSalary(employeeId);
            department.addSubNode(new Employee(employeeId, salary));
        }
    }

    public static void main(String[] args) {
        App app = new App(new DepartmentRepoImpl(), new EmployeeRepoImpl());
    }
}
