package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.repository.ExamRepository;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    public Exam findOne(Integer id) {
        return examRepository.findOne(id);
    }

    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    public void remove(Integer id) {
        examRepository.delete(id);
    }
    
    public List<Exam> findByDuty(Duty duty){
    	return examRepository.findByDuty(duty);
    }
    
    public List<Exam> findByStudent(Student student){
    	return examRepository.findByStudent(student);
    }
    
    public Exam findByStudentAndDuty(Student student, Duty duty){
    	return examRepository.findByStudentAndDuty(student, duty);
    }

}
