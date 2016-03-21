package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	

}
