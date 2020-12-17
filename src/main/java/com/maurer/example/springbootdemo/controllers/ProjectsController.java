package com.maurer.example.springbootdemo.controllers;


import com.maurer.example.springbootdemo.models.Department;
import com.maurer.example.springbootdemo.models.Project;
import com.maurer.example.springbootdemo.models.dto.ProjectDescriptionOnly;
import com.maurer.example.springbootdemo.service.contracts.ProjectService;
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
@RequestMapping("/v1/projects")
public class ProjectsController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "View a list of all company projects", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of projects successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Project>> getAllProjects() {

        logger.debug("Invoking getAllProjects()");

        List<Project> projects = projectService.getAllProjects();

        if(projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
    }


    @ApiOperation(value = "Get company project by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {

        logger.debug("Invoking getProjectById()");

        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Create new project")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Project successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to post")
    })
    @PostMapping
    public ResponseEntity<Project> createNewProject(@RequestBody final Project project) {

        logger.debug("Invoking createNewProject()");

        return new ResponseEntity<>(projectService.createNewProject(project), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update existing project fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project successfully updated"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{id}")
    public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody Project project) {

        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        //todo_update: resolved through patch mapping, put can only be used for complete response body

        logger.debug("Invoking updateProject()");

        Project existingProject = projectService.getProjectById(id);
        BeanUtils.copyProperties(project, existingProject, "project_id");

        return new ResponseEntity<>(projectService.createNewProject(existingProject), HttpStatus.OK);
    }


    @ApiOperation(value = "Update existing project description fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project description successfully updated"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping("{id}")
    public ResponseEntity<Project> updateProjectDescription(@PathVariable int id, @RequestBody ProjectDescriptionOnly projectDescriptionOnly) {

        logger.debug("Invoking updateProjectDescription()");

        Project existingProject = projectService.getProjectById(id);
        BeanUtils.copyProperties(projectDescriptionOnly, existingProject, "project_id");

        return new ResponseEntity<>(projectService.createNewProject(existingProject), HttpStatus.OK);
    }


    @ApiOperation(value = "Delete company project fetched by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project successfully retrieved"),
            @ApiResponse(code = 204, message = "Content not found"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Department> deleteProjectById(@PathVariable int id) {

        logger.debug("Invoking deleteProjectById()");
        projectService.deleteProjectById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
