package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}
