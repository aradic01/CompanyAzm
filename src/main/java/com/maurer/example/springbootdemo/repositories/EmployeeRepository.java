package com.maurer.example.springbootdemo.repositories;

import com.maurer.example.springbootdemo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
