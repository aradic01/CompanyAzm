package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.repositories.DepartmentRepository;
import com.maurer.example.springbootdemo.service.contracts.DepartmentService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments()
    {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(department -> departments.add(department));
        return departments;
    }

    //getting a specific record
    @Override
    public Department getDepartmentById(int id) {

        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department with ID " + id + " Not Found!"));

        return department;
    }

    @Override
    public Department createNewDepartment(Department department)
    {
        departmentRepository.saveAndFlush(department);
        return department;
    }

    @Override
    public void deleteDepartmentById(int id) {

        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if(optionalDepartment.isPresent()) {
            departmentRepository.deleteById(id);
        } else  {
            throw new ResourceNotFoundException("Department with ID " + id + " Not Found!");
        }

    }


}
