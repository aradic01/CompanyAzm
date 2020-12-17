package com.maurer.example.springbootdemo.service.contracts;

import com.maurer.example.springbootdemo.models.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(int id);

    public Employee createNewEmployee(Employee employee);

    public void deleteEmployeeById(int id);

}
