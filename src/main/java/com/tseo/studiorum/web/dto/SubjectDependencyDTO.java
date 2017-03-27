package com.tseo.studiorum.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;

public class SubjectDependencyDTO {
	
	private Integer id;
	private SubjectDTO subject;
	private List<SubjectDTO> requiredSubjects;
	
	public SubjectDependencyDTO() { }
	
	public SubjectDependencyDTO(SubjectDependency subjectDependency){
		this.id = subjectDependency.getId();
		this.subject = new SubjectDTO(subjectDependency.getSubject());
		requiredSubjects = new ArrayList<SubjectDTO>();
		if(subjectDependency.getRequiredSubjects() != null){
			for(Subject subject : subjectDependency.getRequiredSubjects()){
				requiredSubjects.add(new SubjectDTO(subject));
			}
		}
	}
	
	

	public SubjectDependencyDTO(Integer id, SubjectDTO subject, List<SubjectDTO> requiredSubjects) {
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

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public List<SubjectDTO> getRequiredSubjects() {
		return requiredSubjects;
	}

	public void setRequiredSubjects(List<SubjectDTO> requiredSubjects) {
		this.requiredSubjects = requiredSubjects;
	}
	
	
	
	

}
