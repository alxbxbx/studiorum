package com.tseo.studiorum.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.service.SubjectDependencyService;
import com.tseo.studiorum.service.SubjectService;
import com.tseo.studiorum.web.dto.SubjectDependencyDTO;

@RestController
@RequestMapping(value = "api/subjectdependency")
public class SubjectDependencyController {
	
	@Autowired
	private SubjectDependencyService subjectDependencyService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Permission(roles = {"user", "professor", "student"})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SubjectDependencyDTO> getOne(@PathVariable Integer id){
		SubjectDependency subjectDependency = subjectDependencyService.getSubjectDependencyById(id);
		
		if(subjectDependency == null)
			return new ResponseEntity<SubjectDependencyDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<SubjectDependencyDTO>(new SubjectDependencyDTO(subjectDependency), HttpStatus.OK);
	}
	
	@Permission(roles = {"user", "professor", "student"})
	@RequestMapping(value = "/subject/{id}", method = RequestMethod.GET)
	public ResponseEntity<SubjectDependencyDTO> getOneBySubject(@PathVariable Integer id){
		Subject subject = subjectService.findOne(id);
		if(subject == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		SubjectDependency subjectDependency = subjectDependencyService.findBySubject(subject);
		
		if(subjectDependency == null)
			return new ResponseEntity<SubjectDependencyDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<SubjectDependencyDTO>(new SubjectDependencyDTO(subjectDependency), HttpStatus.OK);
	}
	
	@Permission(roles = {"user", "professor"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SubjectDependencyDTO> save(@RequestBody SubjectDependencyDTO subjectDependencyDTO){
		SubjectDependency subjectDependency = new SubjectDependency();
		
		subjectDependency.setSubject(subjectDependencyDTO.getSubject());
		subjectDependency.setRequiredSubjects(subjectDependencyDTO.getRequiredSubjects());
		
		subjectDependency = subjectDependencyService.saveSubjectDependency(subjectDependency);
		
		return new ResponseEntity<SubjectDependencyDTO>(new SubjectDependencyDTO(subjectDependency), HttpStatus.CREATED);
	}
	
	@Permission(roles = {"user", "professor"})
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<SubjectDependencyDTO> update(@RequestBody SubjectDependencyDTO subjectDependencyDTO){
		SubjectDependency subjectDependency = subjectDependencyService.getSubjectDependencyById(subjectDependencyDTO.getId());
		if(subjectDependency == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		subjectDependency.setId(subjectDependencyDTO.getId());
		subjectDependency.setSubject(subjectDependencyDTO.getSubject());
		subjectDependency.setRequiredSubjects(subjectDependencyDTO.getRequiredSubjects());
		
		subjectDependency = subjectDependencyService.saveSubjectDependency(subjectDependency);
		
		return new ResponseEntity<SubjectDependencyDTO>(new SubjectDependencyDTO(subjectDependency), HttpStatus.CREATED);
	}
	
	@Permission(roles = {"user", "professor"})
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void>  delete(@PathVariable Integer id){
		SubjectDependency subjectDependency = subjectDependencyService.getSubjectDependencyById(id);
		if(subjectDependency == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			
		subjectDependencyService.deleteSubjectDependency(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
