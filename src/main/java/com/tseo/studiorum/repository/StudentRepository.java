package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
