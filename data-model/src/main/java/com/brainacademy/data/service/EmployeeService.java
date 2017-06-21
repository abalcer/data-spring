package com.brainacademy.data.service;

import com.brainacademy.data.model.Department;
import com.brainacademy.data.model.Employee;

import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    
    Iterable<Department> getAvailableDepartments();

    Iterable<Employee> searchEmployees(String departmentNumber, String employeeName, Pageable pageable);

    Employee findById(Integer employeeId);

    Employee save(Employee employee);

    Employee save(Employee employee, Department department);
    void delete(Employee employee);
}
