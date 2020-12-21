package com.maurer.example.springbootdemo.models;

import com.maurer.example.springbootdemo.models.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private RoleType name;

    public Role() {}


}
