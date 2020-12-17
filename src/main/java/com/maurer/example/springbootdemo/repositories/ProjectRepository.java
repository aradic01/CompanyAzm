package com.maurer.example.springbootdemo.repositories;

import com.maurer.example.springbootdemo.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
