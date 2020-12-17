package com.maurer.example.springbootdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maurer.example.springbootdemo.models.enums.EmployeeType;
import com.maurer.example.springbootdemo.models.enums.EmployeeTypeConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import com.maurer.example.springbootdemo.models.Department;

@Entity(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Database auto-generated employee ID")
    private int employee_id;

    @ApiModelProperty(notes = "Employee first name")
    private String first_name;

    @ApiModelProperty(notes = "Employee last name")
    private String last_name;

    @ApiModelProperty(notes = "Employee address")
    private String address;

    @ApiModelProperty(notes = "City of residence")
    private String city;

    @ApiModelProperty(notes = "Employee salary")
    private int salary;

    @ApiModelProperty(notes = "Employee type, implies type of work, e.g. engineer")
    private EmployeeType employee_type;

    @ManyToMany
    @JoinTable(
            name = "employee_projects",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )

    @Setter(AccessLevel.NONE)
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "employee_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )

    @Setter(AccessLevel.NONE)
    private List<Department> departments;

    public Employee() {}

}
