package com.tseo.studiorum.web.dto;

import java.util.List;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;

public class SubjectDependencyDTO {
	
	private Integer id;
	private Subject subject;
	private List<Subject> requiredSubjects;
	
	public SubjectDependencyDTO() { }
	
	public SubjectDependencyDTO(SubjectDependency subjectDependency){
		this.id = subjectDependency.getId();
		this.subject = subjectDependency.getSubject();
		this.requiredSubjects = subjectDependency.getRequiredSubjects();
	}
	
	public SubjectDependencyDTO(Integer id, Subject subject, List<Subject> requiredSubjects) {
		super();
		this.id = id;
		this.subject = subject;
		this.requiredSubjects = requiredSubjects;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public List<Subject> getRequiredSubjects() {
		return requiredSubjects;
	}
	public void setRequiredSubjects(List<Subject> requiredSubjects) {
		this.requiredSubjects = requiredSubjects;
	}
	
	

}
