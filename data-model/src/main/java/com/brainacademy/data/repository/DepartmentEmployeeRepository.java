package com.brainacademy.data.repository;

import com.brainacademy.data.model.DepartmentEmployee;
import com.brainacademy.data.model.DepartmentEmployeeId;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentEmployeeRepository
        extends CrudRepository<DepartmentEmployee, DepartmentEmployeeId> {
}
