package com.qpzm7903.structural_patterns.composite.employee;

import java.util.List;

public interface DepartmentRepo {
    List<Long> getSubDepartmentIds(long id);
}
