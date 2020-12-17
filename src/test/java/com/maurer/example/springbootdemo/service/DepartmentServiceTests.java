package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.repositories.DepartmentRepository;
import com.maurer.example.springbootdemo.service.contracts.DepartmentService;
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
public class DepartmentServiceTests {

    @InjectMocks
    private DepartmentService departmentService = new DepartmentServiceImpl();

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private Department departmentMock;

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
    public void getAllDepartmentsShouldReturnDepartmentsList() {

        given(departmentRepository.findAll()).willReturn(departmentList);

        List<Department> expectedList = departmentService.getAllDepartments();

        assertEquals(2, expectedList.size());
        assertEquals(expectedList, departmentList);
    }

    @Test
    public void getDepartmentByExistingIdShouldReturnDepartment() {
        given(departmentRepository.findById(2)).willReturn(Optional.of(otherDepartment));

        Department expectedDepartment = departmentService.getDepartmentById(2);

        assertThat(expectedDepartment).isNotNull();
        assertEquals(expectedDepartment, otherDepartment);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getDepartmentByNonExistingIdShouldThrowResourceNotFoundException() {

        given(departmentRepository.findById(any(Integer.class))).willReturn(Optional.empty());
        departmentService.getDepartmentById(4);
    }

    @Test
    public void createNewDepartmentShouldSuccessfullyCreateNewDepartment() {

        given(departmentRepository.saveAndFlush(any(Department.class))).willAnswer(invocation -> invocation.getArgument(0));
        //given(departmentRepository.saveAndFlush(department)).willReturn(department);

        Department savedDepartment = departmentService.createNewDepartment(department);

        assertThat(savedDepartment).isNotNull();
        verify(departmentRepository).saveAndFlush(any(Department.class));
    }

    @Test
    public void deleteDepartmentShouldDeleteExistingDepartment() {

        final int departmentId = 1;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        departmentService.deleteDepartmentById(departmentId);
        departmentService.deleteDepartmentById(departmentId);

        verify(departmentRepository, times(2)).deleteById(departmentId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNonExistingDepartmentShouldThrowResourceNotFoundException() {

        when(departmentRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        departmentService.deleteDepartmentById(3);
    }

    @Test
    public void relationalMappingShouldSuccessfullyGetEmployees() {
        assertThat(departmentMock.getEmployees()).isNotNull();
    }
}
