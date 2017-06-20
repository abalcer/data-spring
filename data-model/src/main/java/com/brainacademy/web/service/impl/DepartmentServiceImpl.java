package com.brainacademy.web.service.impl;

import com.brainacademy.web.model.Department;
import com.brainacademy.web.repository.DepartmentRepository;
import com.brainacademy.web.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class DepartmentServiceImpl
        implements DepartmentService {

    @Autowired
    private DepartmentRepository  departmentRepository;

    @Override
    public Department getByNumber(String departmentNumber) {
        return departmentRepository.findOne(departmentNumber);
    }
}
