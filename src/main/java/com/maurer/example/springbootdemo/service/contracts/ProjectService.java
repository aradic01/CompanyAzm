package com.maurer.example.springbootdemo.service.contracts;

import com.maurer.example.springbootdemo.models.Project;

import java.util.List;

public interface ProjectService {

    public List<Project> getAllProjects();

    public Project getProjectById(int id);

    public Project createNewProject(Project project);

    public void deleteProjectById(int id);

}
