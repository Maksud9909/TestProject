package org.example.testproject.repository;



import org.example.testproject.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByManagerId(Long managerId, Pageable pageable);
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
    Page<Employee> findByDepartmentName(String departmentName, Pageable pageable);
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findById(Long employeeId);
}
