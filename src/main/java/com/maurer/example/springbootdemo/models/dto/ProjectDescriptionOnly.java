package com.maurer.example.springbootdemo.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model created for PATCH mapping partial update which updates only project description")
public class ProjectDescriptionOnly {

    @ApiModelProperty(notes = "Project ID in Projects database")
    private int project_id;

    @ApiModelProperty(notes = "Project description in Projects database")
    private String description;
}
