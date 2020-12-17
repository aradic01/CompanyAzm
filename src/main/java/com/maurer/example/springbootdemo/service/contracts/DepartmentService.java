package com.maurer.example.springbootdemo.service.contracts;

import com.maurer.example.springbootdemo.models.Department;

import java.util.List;

public interface DepartmentService {

    public List<Department> getAllDepartments();

    public Department getDepartmentById(int id);

    public Department createNewDepartment(Department department);

    public void deleteDepartmentById(int id);
}
