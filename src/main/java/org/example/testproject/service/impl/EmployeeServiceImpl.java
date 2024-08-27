package org.example.testproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.testproject.entity.Employee;
import org.example.testproject.repository.EmployeeRepository;
import org.example.testproject.service.EmployeeService;
import org.hibernate.Hibernate;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(int page, int size, String sort) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than 0.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortField(sort)).ascending());

        Page<Employee> pageObj = employeeRepository.findAll(pageable);
        List<Employee> employees = pageObj.getContent();

        return new PageImpl<>(employees, pageable, pageObj.getTotalElements());
    }

    @Override
    public Page<Employee> findByManagerId(Long managerId, int page, int size, String sort) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than 0.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortField(sort)).ascending());

        Page<Employee> pageObj = employeeRepository.findByManagerId(managerId, pageable);
        List<Employee> employees = pageObj.getContent();

        return new PageImpl<>(employees, pageable, pageObj.getTotalElements());
    }

    @Override
    public Page<Employee> findByDepartment(String department, int page, int size, String sort) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than 0.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortField(sort)).ascending());
        Page<Employee> pageObj;

        // Проверяем, является ли введенное значение числом
        if (isNumeric(department)) {
            pageObj = employeeRepository.findByDepartmentId(Long.parseLong(department), pageable);
        } else {
            pageObj = employeeRepository.findByDepartmentName(department, pageable);
        }

        List<Employee> employees = pageObj.getContent();
        return new PageImpl<>(employees, pageable, pageObj.getTotalElements());
    }


    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public Employee findById(Long id, boolean fullChain) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return null;
        }


        if (employee.getManager() != null) {
            Hibernate.initialize(employee.getManager()); // force download
        }

        if (fullChain) {
            return buildFullManagerChain(employee);
        }

        return employee;
    }



    private Employee buildFullManagerChain(Employee employee) {
        if (employee.getManager() == null) {
            return employee;
        }

        Employee manager = employee.getManager();
        manager = buildFullManagerChain(manager);
        employee.setManager(manager);
        return employee;
    }



    private String getSortField(String sort) {
        switch (sort) {
            case "lastName":
                return "fullName.lastName";
            case "hired":
                return "hiredate";
            case "position":
                return "position";
            case "salary":
                return "salary";
        }
        return sort;
    }

}
