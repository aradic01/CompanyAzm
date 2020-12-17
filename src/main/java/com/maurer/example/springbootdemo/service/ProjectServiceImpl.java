package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.Project;
import com.maurer.example.springbootdemo.repositories.ProjectRepository;
import com.maurer.example.springbootdemo.service.contracts.ProjectService;
import com.maurer.example.springbootdemo.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(project -> projects.add(project));
        return projects;
    }

    //getting a specific record
    public Project getProjectById(int id) {

        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project with ID " + id + " Not Found!"));

        return project;
    }

    public Project createNewProject(Project project) {
        projectRepository.saveAndFlush(project);
        return project;
    }

    @Override
    public void deleteProjectById(int id) {
        Optional<Project> optionalProject = projectRepository.findById(id);

        if(!optionalProject.isPresent()) {
            throw new ResourceNotFoundException("Project with ID " + id + " Not Found!");
        } else {
            projectRepository.deleteById(id);
        }
    }

}
