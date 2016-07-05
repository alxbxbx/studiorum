package com.tseo.studiorum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer>{
	List<Exam> findByDuty(Duty duty);
}
