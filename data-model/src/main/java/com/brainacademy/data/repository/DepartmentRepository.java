package com.brainacademy.data.repository;

import com.brainacademy.data.model.Department;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
        extends CrudRepository<Department, String> {
}
