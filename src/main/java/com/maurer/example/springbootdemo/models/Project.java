package com.maurer.example.springbootdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Database auto generated project ID")
    private int project_id;

    @ApiModelProperty(notes = "Short name of the project")
    private String short_name;

    @ApiModelProperty(notes = "Full name of the project")
    private String name;

    @ApiModelProperty(notes = "Description of the project")
    private String description;

    @ManyToMany(mappedBy = "projects")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Employee> employees;

    public Project() {}

}

