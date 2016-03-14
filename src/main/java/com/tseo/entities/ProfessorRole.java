package com.tseo.entities;

public class ProfessorRole {
	
	private Professor professor;
	private String role;
	
	public ProfessorRole(){}
	
	public ProfessorRole(Professor professor, String role) {
		super();
		this.professor = professor;
		this.role = role;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
