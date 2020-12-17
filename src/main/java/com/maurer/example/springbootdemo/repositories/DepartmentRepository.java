package com.maurer.example.springbootdemo.repositories;

import com.maurer.example.springbootdemo.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
