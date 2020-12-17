package com.maurer.example.springbootdemo.controllers;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.service.contracts.DepartmentService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentsControllerTests {

    @InjectMocks
    DepartmentsController departmentsController;

    @Mock
    private DepartmentService departmentService;

    private List<Department> departmentList = new ArrayList<>();
    private Department department = new Department();
    private Department otherDepartment = new Department();


    @Before
    public void setUp() {

        department.setDepartment_id(1);
        department.setName("Mobile");

        otherDepartment.setDepartment_id(2);
        otherDepartment.setName("DevOps");

        departmentList.add(department);
        departmentList.add(otherDepartment);
    }

    @Test
    public void shouldReturnAllDepartments() {

        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        ResponseEntity<List<Department>> expected = departmentsController.getAllDepartments();
        ResponseEntity<List<Department>> actual = new ResponseEntity<>(departmentList, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldGetDepartmentById() {

        when(departmentService.getDepartmentById(2)).thenReturn(otherDepartment);

        ResponseEntity<Department> expected = departmentsController.getDepartmentById(2);
        ResponseEntity<Department> actual = new ResponseEntity<>(otherDepartment, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionForNonExistingDepartmentId() {

        int departmentId = 3;

        when(departmentService.getDepartmentById(departmentId)).thenThrow(ResourceNotFoundException.class);

        departmentsController.getDepartmentById(departmentId);
    }

    @Test
    public void shouldCreateNewDepartment() {

        given(departmentService.createNewDepartment(any(Department.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Department> expected = departmentsController.createNewDepartment(department);
        ResponseEntity<Department> actual = new ResponseEntity<>(department, HttpStatus.CREATED);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateExistingDepartment() {

        when(departmentService.getDepartmentById(any(Integer.class))).thenReturn(department);
        given(departmentService.createNewDepartment(any(Department.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Department> expected = departmentsController.updateDepartment(1, otherDepartment);
        ResponseEntity<Department> actual = new ResponseEntity<>(department, HttpStatus.OK);

        assertThat(expected.getBody().getName()).isEqualTo(otherDepartment.getName());
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldDeleteExistingDepartmentById() {

        int departmentId = 2;

        departmentService.deleteDepartmentById(departmentId);
        departmentService.deleteDepartmentById(departmentId);

        verify(departmentService, times(2)).deleteDepartmentById(departmentId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhileDeletingNonExistingDepartment() {

        doThrow(ResourceNotFoundException.class).when(departmentService).deleteDepartmentById(any(Integer.class));

        departmentsController.deleteDepartmentById(3);
    }
}
