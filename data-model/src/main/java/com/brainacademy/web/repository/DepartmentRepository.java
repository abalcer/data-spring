package com.brainacademy.web.repository;


import com.brainacademy.web.model.Department;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
        extends CrudRepository<Department, String> {
}
