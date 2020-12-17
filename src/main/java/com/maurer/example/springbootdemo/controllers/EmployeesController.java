package com.maurer.example.springbootdemo.controllers;


import com.maurer.example.springbootdemo.models.Employee;
import com.maurer.example.springbootdemo.models.dto.EmployeeAddressOnly;
import com.maurer.example.springbootdemo.service.contracts.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/employees")
public class EmployeesController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "View a list of all company employees", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of employees successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Employee>> getALlEmployees() {

        logger.debug("Invoking getAllEmployees()");

        List<Employee> employees = employeeService.getAllEmployees();

        if(employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }


    @ApiOperation(value = "Get company employee by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {

        logger.debug("Invoking getEmployeeById()");

        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to post")
    })
    @PostMapping
    public ResponseEntity<Employee> createNewEmployee(@RequestBody final Employee employee) {

        logger.debug("Invoking createNewEmployee()");

        return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update existing employee fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {

        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        //todo_update: resolved through patch mapping, put can only be used for complete response body

        logger.debug("Invoking updateEmployee()");

        Employee existingEmployee = employeeService.getEmployeeById(id);
        BeanUtils.copyProperties(employee, existingEmployee, "employee_id");

        return new ResponseEntity<>(employeeService.createNewEmployee(existingEmployee), HttpStatus.OK);
    }


    @ApiOperation(value = "Update existing employee address, fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee address successfully updated"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeAddress(@PathVariable int id, @RequestBody EmployeeAddressOnly employeeAddressOnly) {

        logger.debug("Invoking updateEmployeeAddress()");

        Employee existingEmployee = employeeService.getEmployeeById(id);
        BeanUtils.copyProperties(employeeAddressOnly, existingEmployee, "employee_id");

        return new ResponseEntity<>(employeeService.createNewEmployee(existingEmployee), HttpStatus.OK);
    }


    @ApiOperation(value = "Delete company employee fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully retrieved"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable int id) {

        logger.debug("Invoking deleteEmployeeById()");
        employeeService.deleteEmployeeById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
