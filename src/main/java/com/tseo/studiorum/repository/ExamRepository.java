package com.tseo.studiorum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Student;

public interface ExamRepository extends JpaRepository<Exam, Integer>{
	List<Exam> findByDuty(Duty duty);
	List<Exam> findByStudent(Student student);
	Exam findByStudentAndDuty(Student student, Duty duty);
}
