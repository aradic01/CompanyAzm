package com.maurer.example.springbootdemo.controllers;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.service.contracts.DepartmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/departments")
public class DepartmentsController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentService departmentService;


    @ApiOperation(value = "View a list of all company departments", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of departments successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAllDepartments() {

        logger.debug("Invoking getAllDepartments()");

        List<Department> departments = departmentService.getAllDepartments();

        if(departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(departments, HttpStatus.OK);
        }
    }


    @ApiOperation(value = "Get company department by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {

        logger.debug("Invoking getDepartmentById()");

        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Create new department")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Department successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to post")
    })
    @PostMapping
    public ResponseEntity<Department> createNewDepartment(@RequestBody final Department department) {

        logger.debug("Invoking createNewDepartment()");

        return new ResponseEntity<>(departmentService.createNewDepartment(department), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update existing department fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully updated"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        //todo_update: resolved through patch mapping, put can only be used for complete response body

        logger.debug("Invoking updateDepartment()");

        Department existingDepartment = departmentService.getDepartmentById(id);
        BeanUtils.copyProperties(department, existingDepartment, "department_id");

        return new ResponseEntity<>(departmentService.createNewDepartment(existingDepartment), HttpStatus.OK);

    }


    @ApiOperation(value = "Delete company department fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully retrieved"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Department> deleteDepartmentById(@PathVariable int id) {

        logger.debug("Invoking deleteDepartmentById()");
        departmentService.deleteDepartmentById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
