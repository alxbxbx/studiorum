package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.Subject;

public class SubjectDTO {
	
	private Integer id;
	private String name;
	private Integer semester;
	private String description;
	
	public SubjectDTO(Subject subject){
		this.id = subject.getId();
		this.name = subject.getName();
		this.semester = subject.getSemester();
		this.description = subject.getDescription();
	}
	
	public SubjectDTO(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
