package com.tseo.studiorum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.repository.SubjectDependencyRepository;

@Service
public class SubjectDependencyService {
	
	@Autowired
	private SubjectDependencyRepository subjectDependencyRepository;
	
	public SubjectDependency getSubjectDependencyServiceById(Integer id){
		return subjectDependencyRepository.findOne(id);
	}
	
	public SubjectDependency saveSubjectDependency(SubjectDependency subjectDependency){
		return subjectDependencyRepository.save(subjectDependency);
	}
	
	public void deleteSubjectDependency(Integer id){
		subjectDependencyRepository.delete(id);
	}
}
