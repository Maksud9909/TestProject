package org.example.testproject.controller;



import lombok.RequiredArgsConstructor;
import org.example.testproject.entity.Employee;
import org.example.testproject.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                  @RequestParam(value = "sort", required = false) String sort) {
        Page<Employee> result = employeeService.findAll(page, size, sort);
        return result.getContent();
    }



    @GetMapping("/{employee_id}")
    public Employee findById(@PathVariable("employee_id") Long id, @RequestParam("fullChain") boolean fullChain) {
        return employeeService.findById(id, fullChain);
    }


    @GetMapping("/by_manager/{manager_id}")
    public List<Employee> findByManagerId(@PathVariable("manager_id") Long managerId,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                          @RequestParam(value = "sort", required = false) String sort) {
        Page<Employee> result = employeeService.findByManagerId(managerId, page, size, sort);
        return result.getContent();
    }


    @GetMapping("/by_department/{department}")
    public List<Employee> findByDepartmentId(@PathVariable(value = "department") String department,
                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                             @RequestParam(value = "sort", required = false) String sort){
        Page<Employee> result = employeeService.findByDepartment(department,page,size,sort);
        return result.getContent();
    }
}
