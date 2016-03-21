package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	SubjectRepository subjectRepository;
	
	public Subject findOne(Integer id){
		return subjectRepository.findOne(id);
		
	}
	
	public List<Subject> findAll(){
		return subjectRepository.findAll();
	}
	
	public Subject save(Subject subject){
		return subjectRepository.save(subject);
	}
	
	public void remove(Integer id){
		subjectRepository.delete(id);
	}
	
}
