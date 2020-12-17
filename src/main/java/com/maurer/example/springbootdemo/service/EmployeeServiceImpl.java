package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Employee;
import com.maurer.example.springbootdemo.repositories.EmployeeRepository;
import com.maurer.example.springbootdemo.service.contracts.EmployeeService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    //getting all employee records
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employees.add(employee));
        return employees;
    }

    //getting a specific record
    public Employee getEmployeeById(int id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with ID " + id + " Not Found!"));

        return employee;
    }

    public Employee createNewEmployee(Employee employee) {
        employeeRepository.saveAndFlush(employee);
        return employee;
    }


    //deleting a specific record
    public void deleteEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(!optionalEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with ID " + id + " Not Found!");
        } else {
            employeeRepository.deleteById(id);
        }
    }
}

