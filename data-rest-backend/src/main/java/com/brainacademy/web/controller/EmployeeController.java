package com.brainacademy.web.controller;


import com.brainacademy.data.model.Department;
import com.brainacademy.data.model.Employee;
import com.brainacademy.data.service.DepartmentService;
import com.brainacademy.data.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Employee> findEmployees(
            @RequestParam(name = "departmentNumber") String departmentNumber,
            @RequestParam(name = "employeeName", required = false) String employeeName,
            Pageable pageable) {

        return employeeService.searchEmployees(departmentNumber, employeeName, pageable);
    }

    @RequestMapping(path = "/{employeeId}", method = RequestMethod.DELETE)
    public Employee delete(@PathVariable Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        employeeService.delete(employee);
        return employee;
    }

    @RequestMapping(path = "/department/{departmentNumber}", method = RequestMethod.POST)
    public Employee save(@PathVariable String departmentNumber, @RequestBody Employee employeeParam) {
        if (employeeParam.getId() != null) {
            Employee employee = employeeService.findById(employeeParam.getId());
            if (employee == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            employee.setFirstName(employeeParam.getFirstName());
            employee.setLastName(employeeParam.getLastName());
            employee.setBirthDate(employeeParam.getBirthDate());
            employee.setHireDate(employeeParam.getHireDate());
            employee.setGender(employeeParam.getGender());
            return employeeService.save(employee);
        } else {
            Department department = departmentService.getByNumber(departmentNumber);
            if (department == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            return employeeService.save(employeeParam, department);
        }
    }
}
