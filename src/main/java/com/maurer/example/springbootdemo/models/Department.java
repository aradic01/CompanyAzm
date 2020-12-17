package com.maurer.example.springbootdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "departments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Database auto-generated department ID")
    private int department_id;

    @ApiModelProperty(notes = "Name of the company department")
    private String name;

    //Use with caution, when deleting a parent entity, all related child objects are deleted as well
    //@ManyToMany(mappedBy = "departments", cascade = CascadeType.DELETE)
    @ManyToMany(mappedBy = "departments")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Employee> employees;

    public Department() {}
}
