package com.maurer.example.springbootdemo.repositories;

import com.maurer.example.springbootdemo.models.Role;
import com.maurer.example.springbootdemo.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
