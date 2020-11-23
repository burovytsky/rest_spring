package com.burovytsky.rest_spring.repository;

import com.burovytsky.rest_spring.domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
