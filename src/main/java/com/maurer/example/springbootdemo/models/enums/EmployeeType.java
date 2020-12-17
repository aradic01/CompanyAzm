package com.maurer.example.springbootdemo.models.enums;

public enum EmployeeType {
    MANAGER("Manager"),
    SECRETARY("Secretary"),
    DIRECTOR("Director"),
    ENGINEER("Engineer");

    private String code;

    private EmployeeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
