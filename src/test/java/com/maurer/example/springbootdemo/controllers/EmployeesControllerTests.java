package com.maurer.example.springbootdemo.controllers;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.models.Employee;
import com.maurer.example.springbootdemo.models.dto.EmployeeAddressOnly;
import com.maurer.example.springbootdemo.models.enums.EmployeeType;
import com.maurer.example.springbootdemo.service.contracts.EmployeeService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class EmployeesControllerTests {

    @InjectMocks
    EmployeesController employeesController;

    @Mock
    private EmployeeService employeeService;

    private List<Employee> employeeList = new ArrayList<>();
    private Employee employee = new Employee();
    private Employee otherEmployee = new Employee();


    @Before
    public void setUp() {

        employee.setEmployee_id(1);
        employee.setFirst_name("Fr1");
        employee.setLast_name("Ln1");
        employee.setSalary(1000);
        employee.setAddress("Address1");
        employee.setCity("City1");
        employee.setEmployee_type(EmployeeType.ENGINEER);


        otherEmployee.setEmployee_id(2);
        otherEmployee.setFirst_name("Fr2");
        otherEmployee.setLast_name("Ln2");
        otherEmployee.setSalary(2000);
        otherEmployee.setAddress("Address2");
        otherEmployee.setCity("City2");
        otherEmployee.setEmployee_type(EmployeeType.MANAGER);

        employeeList.add(employee);
        employeeList.add(otherEmployee);
    }

    @Test
    public void shouldReturnAllEmployees() {

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResponseEntity<List<Employee>> expected = employeesController.getALlEmployees();
        ResponseEntity<List<Employee>> actual = new ResponseEntity<>(employeeList, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldGetEmployeeById() {

        when(employeeService.getEmployeeById(2)).thenReturn(otherEmployee);

        ResponseEntity<Employee> expected = employeesController.getEmployeeById(2);
        ResponseEntity<Employee> actual = new ResponseEntity<>(otherEmployee, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionForNonExistingEmployeeId() {

        int employeeId = 3;

        when(employeeService.getEmployeeById(employeeId)).thenThrow(ResourceNotFoundException.class);

        employeesController.getEmployeeById(employeeId);
    }

    @Test
    public void shouldCreateNewEmployee() {

        given(employeeService.createNewEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Employee> expected = employeesController.createNewEmployee(employee);
        ResponseEntity<Employee> actual = new ResponseEntity<>(employee, HttpStatus.CREATED);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateEmployeeAddressOnly() {

        EmployeeAddressOnly employeeAddressOnly = new EmployeeAddressOnly(); //arrange
        employeeAddressOnly.setAddress("Oak Street 8");

        when(employeeService.getEmployeeById(any(Integer.class))).thenReturn(employee); //act
        when(employeeService.createNewEmployee(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Employee> expected = employeesController.updateEmployeeAddress(1, employeeAddressOnly);
        ResponseEntity<Employee> actual = new ResponseEntity<>(employee, HttpStatus.OK);

        assertThat(actual.getBody().getAddress()).isEqualTo(employeeAddressOnly.getAddress()); //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateExistingEmployee() {

        when(employeeService.getEmployeeById(any(Integer.class))).thenReturn(employee);
        given(employeeService.createNewEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Employee> expected = employeesController.updateEmployee(1, otherEmployee);
        ResponseEntity<Employee> actual = new ResponseEntity<>(employee, HttpStatus.OK);

        assertThat(expected.getBody().getFirst_name()).isEqualTo(otherEmployee.getFirst_name());
        assertThat(expected.getBody().getLast_name()).isEqualTo(otherEmployee.getLast_name());
        assertThat(expected.getBody().getAddress()).isEqualTo(otherEmployee.getAddress());
        assertThat(expected.getBody().getCity()).isEqualTo(otherEmployee.getCity());
        assertThat(expected.getBody().getSalary()).isEqualTo(otherEmployee.getSalary());
        assertThat(expected.getBody().getEmployee_type()).isEqualTo(otherEmployee.getEmployee_type());

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldDeleteExistingEmployeeById() {

        int employeeId = 2;

        employeeService.deleteEmployeeById(employeeId);
        employeeService.deleteEmployeeById(employeeId);

        verify(employeeService, times(2)).deleteEmployeeById(employeeId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhileDeletingNonExistingEmployee() {

        doThrow(ResourceNotFoundException.class).when(employeeService).deleteEmployeeById(any(Integer.class));

        employeesController.deleteEmployeeById(3);
    }
}
