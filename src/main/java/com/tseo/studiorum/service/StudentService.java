package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	public Student findOne(Integer id){
		return studentRepository.findOne(id);
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}
	
	public Student save(Student student){
		return studentRepository.save(student);
	}
	
	public void remove(Integer id){
		studentRepository.delete(id);
	}
	
}
