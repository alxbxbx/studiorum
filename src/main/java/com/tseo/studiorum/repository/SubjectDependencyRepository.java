package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;

public interface SubjectDependencyRepository extends JpaRepository<SubjectDependency, Integer>{
	
	SubjectDependency findBySubject(Subject subject);
}
