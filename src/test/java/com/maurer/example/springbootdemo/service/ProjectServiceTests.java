package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Project;
import com.maurer.example.springbootdemo.repositories.ProjectRepository;
import com.maurer.example.springbootdemo.service.contracts.ProjectService;
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
public class ProjectServiceTests {

    @InjectMocks
    ProjectService projectService = new ProjectServiceImpl();

    @Mock
    ProjectRepository projectRepository;

    @Mock
    Project projectMock;

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

        given(projectRepository.findAll()).willReturn(projectList);

        List<Project> expectedList = projectService.getAllProjects();

        assertEquals(2, expectedList.size());
        assertEquals(expectedList, projectList);
    }

    @Test
    public void shouldReturnExistingProjectById() {

        given(projectRepository.findById(2)).willReturn(Optional.of(otherProject));

        Project expectedProject = projectService.getProjectById(2);

        assertThat(expectedProject).isNotNull();
        assertEquals(expectedProject, otherProject);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhileRetrievingNonExistingProject() {

        given(projectRepository.findById(4)).willReturn(Optional.empty());
        projectService.getProjectById(4);
    }

    @Test
    public void shouldSuccessfullyCreateNewProject() {

        given(projectRepository.saveAndFlush(project)).willAnswer(invocation -> invocation.getArgument(0));
        //given(departmentRepository.saveAndFlush(department)).willReturn(department);

        Project savedProject = projectService.createNewProject(project);

        assertThat(savedProject).isNotNull();
        verify(projectRepository).saveAndFlush(any(Project.class));
    }

    @Test
    public void shouldDeleteExistingProjectById() {

        final int projectId = 1;

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.deleteProjectById(projectId);
        projectService.deleteProjectById(projectId);

        verify(projectRepository, times(2)).deleteById(projectId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhileTryingToDeleteNonExistingProject() {

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        projectService.deleteProjectById(3);
    }

    @Test
    public void shouldSuccessfullyGetAllEmployeesInRelation() {
        assertThat(projectMock.getEmployees()).isNotNull();
    }
}
