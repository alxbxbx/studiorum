package com.tseo.studiorum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.tseo.studiorum.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	List<Student> findByFirstLastName(@Param("searchText")String searchText);
}
