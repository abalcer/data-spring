package com.brainacademy.web.service.impl;

import com.brainacademy.web.model.Department;
import com.brainacademy.web.model.Employee;
import com.brainacademy.web.repository.DepartmentRepository;
import com.brainacademy.web.repository.EmployeeRepository;
import com.brainacademy.web.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
class EmployeeServiceImpl
        implements EmployeeService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Department> getAvailableDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Iterable<Employee> searchEmployees(String departmentNumber, String employeeName, Pageable pageable) {
        if (!StringUtils.isEmpty(employeeName)) {
            String[] parts = employeeName.split(" ");
            if (parts.length == 1) {
                return employeeRepository.findEmployees(departmentNumber, "%" + employeeName + "%", pageable);
            } else {
                return employeeRepository.findEmployees(departmentNumber,
                        "%" + parts[0] + "%",
                        "%" + parts[1] + "%",
                        pageable);
            }
        } else {
            return employeeRepository.findByDepartments_Number(departmentNumber, pageable);
        }
    }

    @Override
    public Employee findById(Integer employeeId) {
        return employeeRepository.findOne(employeeId);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
