package com.maurer.example.springbootdemo.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model created for PATCH mapping partial update which updates only employee's address")
public class EmployeeAddressOnly {

    @ApiModelProperty(notes = "Employee ID in Employees database")
    private Integer employee_id;

    @ApiModelProperty(notes = "Employee address in Employees database")
    private String address;
}
