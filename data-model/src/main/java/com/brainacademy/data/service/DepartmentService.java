package com.brainacademy.data.service;

import com.brainacademy.data.model.Department;

public interface DepartmentService {
    Department getByNumber(String departmentNumber);
    Department save(Department department);
}
