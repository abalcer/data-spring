package com.brainacademy.data.service.impl;

import com.brainacademy.data.model.Department;
import com.brainacademy.data.repository.DepartmentRepository;
import com.brainacademy.data.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class DepartmentServiceImpl
        implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department getByNumber(String departmentNumber) {
        return departmentRepository.findOne(departmentNumber);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
}
