package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.StudentSubject;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.repository.StudentSubjectRepository;

@Service
public class StudentSubjectService {
	
	@Autowired
	private StudentSubjectRepository studentSubjectRepository;
	
	public StudentSubject getStudentSubjectById(Integer id){
		return studentSubjectRepository.getOne(id);
	}
	
	public List<StudentSubject> findByStudent(Student student){
		return studentSubjectRepository.findByStudent(student);
	}
	
	public StudentSubject findByStudentAndSubject(Student student, Subject subject){
		return studentSubjectRepository.findByStudentAndSubject(student, subject);
	}
	
	public StudentSubject saveStudentSubject(StudentSubject studentSubject){
		return studentSubjectRepository.save(studentSubject);
	}
	
	public StudentSubject saveStudentSubject(Student student, Subject subject){
		//TODO Implement logic to check if subject is available for student, set pass to FALSE
		
		return null;
	}
	
	public void checkIfPass(StudentSubject studentSubject){
		//TODO Check every exam and sum them up, if sum > 54 pass = TRUE
	}
	
	public void deleteStudentSubject(Integer id){
		studentSubjectRepository.delete(id);
	}
	
	public void deleteStudentSubject(StudentSubject studentSubject){
		studentSubjectRepository.delete(studentSubject);
	}

}
