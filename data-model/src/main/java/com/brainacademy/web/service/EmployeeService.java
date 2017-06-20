package com.brainacademy.web.service;

import com.brainacademy.web.model.Department;
import com.brainacademy.web.model.Employee;

import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    
    Iterable<Department> getAvailableDepartments();

    Iterable<Employee> searchEmployees(String departmentNumber, String employeeName, Pageable pageable);

    Employee findById(Integer employeeId);

    Employee save(Employee employee);

    void delete(Employee employee);
}
