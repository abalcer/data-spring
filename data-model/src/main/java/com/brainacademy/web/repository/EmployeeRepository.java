package com.brainacademy.web.repository;


import com.brainacademy.web.model.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository
        extends CrudRepository<Employee, Integer> {

    Page<Employee> findByDepartments_Number(String departmentNumber, Pageable pageable);

    @Query(value = "select e from Employee e left join e.departments as department "
            + "where department.number=:departmentNumber "
            + "   and (e.firstName like :name or e.lastName like :name)")
    Page<Employee> findEmployees(@Param("departmentNumber") String departmentNumber,
                                 @Param("name") String name,
                                 Pageable pageable);

    @Query(value = "select e from Employee e left join e.departments as department "
            + "where department.number=:departmentNumber "
            + "   and (e.firstName like :firstName and e.lastName like :lastName)")
    Page<Employee> findEmployees(@Param("departmentNumber") String departmentNumber,
                                 @Param("firstName") String firstName,
                                 @Param("lastName") String lastName,
                                 Pageable pageable);
}
