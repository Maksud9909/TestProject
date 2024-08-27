package org.example.testproject.service;

import org.example.testproject.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Page<Employee> findAll(int page, int size,String sort);
    Page<Employee> findByDepartment(String department, int page, int size, String sort);
    Employee findById(Long id, boolean fullChain);
    Page<Employee> findByManagerId(Long managerId,int page, int size,String sort);
}

