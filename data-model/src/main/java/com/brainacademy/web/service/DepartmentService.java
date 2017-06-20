package com.brainacademy.web.service;


import com.brainacademy.web.model.Department;

public interface DepartmentService {
    Department getByNumber(String departmentNumber);
}
