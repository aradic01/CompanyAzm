package com.maurer.example.springbootdemo.controllers;

import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.models.Employee;
import com.maurer.example.springbootdemo.models.Project;
import com.maurer.example.springbootdemo.models.dto.EmployeeAddressOnly;
import com.maurer.example.springbootdemo.models.dto.ProjectDescriptionOnly;
import com.maurer.example.springbootdemo.service.contracts.DepartmentService;
import com.maurer.example.springbootdemo.service.contracts.ProjectService;
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
public class ProjectsControllerTests {

    @InjectMocks
    ProjectsController projectsController;

    @Mock
    private ProjectService projectService;

    private List<Project> projectList = new ArrayList<>();
    private Project project = new Project();
    private Project otherProject = new Project();


    @Before
    public void setUp() {

        project.setProject_id(1);
        project.setName("PrName1");
        project.setShort_name("PrShort1");
        project.setDescription("Desc1");


        otherProject.setProject_id(2);
        otherProject.setName("PrName2");
        otherProject.setShort_name("PrShort2");
        otherProject.setDescription("Desc2");

        projectList.add(project);
        projectList.add(otherProject);
    }

    @Test
    public void shouldReturnAllProjects() {

        when(projectService.getAllProjects()).thenReturn(projectList);

        ResponseEntity<List<Project>> expected = projectsController.getAllProjects();
        ResponseEntity<List<Project>> actual = new ResponseEntity<>(projectList, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldGetProjectById() {

        when(projectService.getProjectById(2)).thenReturn(otherProject);

        ResponseEntity<Project> expected = projectsController.getProjectById(2);
        ResponseEntity<Project> actual = new ResponseEntity<>(otherProject, HttpStatus.OK);

        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionForNonExistingProjectId() {

        int projectId = 3;

        when(projectService.getProjectById(projectId)).thenThrow(ResourceNotFoundException.class);

        projectsController.getProjectById(projectId);
    }

    @Test
    public void shouldCreateNewProject() {

        given(projectService.createNewProject(any(Project.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Project> expected = projectsController.createNewProject(project);
        ResponseEntity<Project> actual = new ResponseEntity<>(project, HttpStatus.CREATED);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateExistingProject() {

        when(projectService.getProjectById(any(Integer.class))).thenReturn(project);
        given(projectService.createNewProject(any(Project.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Project> expected = projectsController.updateProject(1, otherProject);
        ResponseEntity<Project> actual = new ResponseEntity<>(project, HttpStatus.OK);

        assertThat(expected.getBody().getName()).isEqualTo(otherProject.getName());
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateProjectDescriptionOnly() {

        ProjectDescriptionOnly projectDescriptionOnly = new ProjectDescriptionOnly(); //arrange
        projectDescriptionOnly.setDescription("New description");

        when(projectService.getProjectById(any(Integer.class))).thenReturn(project); //act
        when(projectService.createNewProject(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Project> expected = projectsController.updateProjectDescription(1, projectDescriptionOnly);
        ResponseEntity<Project> actual = new ResponseEntity<>(project, HttpStatus.OK);

        assertThat(actual.getBody().getDescription()).isEqualTo(projectDescriptionOnly.getDescription()); //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldDeleteExistingProjectById() {

        int projectId = 2;

        projectService.deleteProjectById(projectId);
        projectService.deleteProjectById(projectId);

        verify(projectService, times(2)).deleteProjectById(projectId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhileDeletingNonExistingProject() {

        doThrow(ResourceNotFoundException.class).when(projectService).deleteProjectById(any(Integer.class));

        projectsController.deleteProjectById(3);
    }
}
