package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.repository.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	ProfessorRepository professorRepository;
	
	public Professor findOne(Integer id){
		return professorRepository.findOne(id);
	}
	
	public List<Professor> findAll(){
		return professorRepository.findAll();
	}
	
	public Professor save(Professor professor){
		return professorRepository.save(professor);
	}
	
	public void remove(Integer id){
		professorRepository.delete(id);
	}

}
