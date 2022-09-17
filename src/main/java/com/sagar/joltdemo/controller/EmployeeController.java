package com.sagar.joltdemo.controller;

import com.sagar.joltdemo.entity.Employee;
import com.sagar.joltdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> employeeList() {
        return employeeRepository.findAll();
    }
}
