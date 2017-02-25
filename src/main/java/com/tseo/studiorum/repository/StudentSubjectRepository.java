package com.tseo.studiorum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.StudentSubject;
import com.tseo.studiorum.entities.Subject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer>{
	
	StudentSubject findByStudentAndSubject(Student student, Subject subject);
	
	List<StudentSubject> findByStudent(Student student);
	
}
