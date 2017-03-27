package com.tseo.studiorum.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class SubjectDependency {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Subject subject;
	
	@OneToMany
	@JsonBackReference
	private List<Subject> requiredSubjects;
	
	
	public SubjectDependency(){
		this.requiredSubjects = new ArrayList<Subject>();
	}
	
	public SubjectDependency(Subject subject, List<Subject> requiredSubjects) {
		super();
		this.subject = subject;
		this.requiredSubjects = requiredSubjects;
	}

	public SubjectDependency(Integer id, Subject subject, List<Subject> requiredSubjects) {
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
