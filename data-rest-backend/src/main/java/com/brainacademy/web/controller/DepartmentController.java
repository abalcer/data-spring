package com.brainacademy.web.controller;

import com.brainacademy.data.model.Department;
import com.brainacademy.data.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/department")
public class DepartmentController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Department> getDepartments() {
        return employeeService.getAvailableDepartments();
    }
}
