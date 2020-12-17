package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Employee;
import com.maurer.example.springbootdemo.models.enums.EmployeeType;
import com.maurer.example.springbootdemo.repositories.EmployeeRepository;
import com.maurer.example.springbootdemo.service.contracts.EmployeeService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTests {

    @InjectMocks
    EmployeeService employeeService = new EmployeeServiceImpl();

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    Employee employeeMock = mock(Employee.class);


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
    public void getAllEmployeesShouldReturnEmployeesList() {

        given(employeeRepository.findAll()).willReturn(employeeList);

        List<Employee> expectedList = employeeService.getAllEmployees();

        assertEquals(2, expectedList.size());
        assertEquals(expectedList, employeeList);
    }

    @Test
    public void getEmployeeByExistingIdShouldReturnEmployee() {
        given(employeeRepository.findById(2)).willReturn(Optional.of(otherEmployee));

        Employee expectedEmployee = employeeService.getEmployeeById(2);

        assertThat(expectedEmployee).isNotNull();
        assertEquals(expectedEmployee, otherEmployee);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getEmployeeByNonExistingIdShouldThrowResourceNotFoundException() {

        given(employeeRepository.findById(4)).willReturn(Optional.empty());
        employeeService.getEmployeeById(4);
    }

    @Test
    public void createNewEmployeeShouldSuccessfullyCreateNewEmployee() {

        given(employeeRepository.saveAndFlush(employee)).willAnswer(invocation -> invocation.getArgument(0));
        //given(departmentRepository.saveAndFlush(department)).willReturn(department);

        Employee savedEmployee = employeeService.createNewEmployee(employee);

        assertThat(savedEmployee).isNotNull();
        verify(employeeRepository).saveAndFlush(any(Employee.class));
    }

    @Test
    public void deleteEmployeeShouldDeleteExistingEmployee() {

        final int employeeId = 1;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployeeById(employeeId);
        employeeService.deleteEmployeeById(employeeId);

        verify(employeeRepository, times(2)).deleteById(employeeId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNonExistingDepartmentShouldThrowResourceNotFoundException() {

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        employeeService.deleteEmployeeById(3);
    }

    @Test
    public void relationalMappingShouldSuccessfullyGetProjectsList() {
        assertThat(employeeMock.getProjects()).isNotNull();
    }

    @Test
    public void relationalMappingShouldSuccessfullyGetDepartmentsList () {
        assertThat(employeeMock.getDepartments()).isNotNull();
    }
}
