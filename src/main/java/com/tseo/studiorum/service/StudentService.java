package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Student> findAll(Pageable page){
		return studentRepository.findAll(page);
	}
	
	public Page<Student> findByFirstLastName(Pageable page, String searchText){
		List<Student> allStudents = studentRepository.findByFirstLastName(searchText);
		Page<Student> students = new PageImpl<>(allStudents, page, allStudents.size());
		return students;
	}
	
	public Integer searchCount(String searchText){
		return studentRepository.findByFirstLastName(searchText).size();
	}
	
	public Student save(Student student){
		return studentRepository.save(student);
	}
	
	public void remove(Integer id){
		studentRepository.delete(id);
	}
	
}
