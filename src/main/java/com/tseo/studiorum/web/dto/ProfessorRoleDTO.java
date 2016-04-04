package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.ProfessorRole;

public class ProfessorRoleDTO {
	private Integer id;
	private String role;
	private ProfessorDTO professorDTO;
	private SubjectDTO subjectDTO;
	
	public ProfessorRoleDTO(ProfessorRole professorRole){
		this.id = professorRole.getId();
		this.role = professorRole.getRole();
		this.professorDTO = new ProfessorDTO(professorRole.getProfessor());
		this.subjectDTO = new SubjectDTO(professorRole.getSubject());
	}
	
	public ProfessorRoleDTO(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public ProfessorDTO getProfessorDTO() {
		return professorDTO;
	}
	public void setProfessorDTO(ProfessorDTO professorDTO) {
		this.professorDTO = professorDTO;
	}
	public SubjectDTO getSubjectDTO() {
		return subjectDTO;
	}
	public void setSubjectDTO(SubjectDTO subjectDTO) {
		this.subjectDTO = subjectDTO;
	}
	
	
}
