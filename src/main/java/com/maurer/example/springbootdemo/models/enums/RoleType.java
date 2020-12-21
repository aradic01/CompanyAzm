package com.maurer.example.springbootdemo.models.enums;

public enum RoleType {
    ROLE_USER("User"),
    ROLE_MODERATOR("Moderator"),
    ROLE_ADMIN("Admin");

    private String code;

    private RoleType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
