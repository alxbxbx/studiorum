package com.tseo.entities;

import java.util.ArrayList;

public class Subject {
	
	private Integer id;
	private String name;
	private Integer semester;
	private String description;
	private ArrayList<Duty> duties;
	private ArrayList<ProfessorRole>  professorRole;
	
	
	public Subject(){
		
	}
	
	public Subject(Integer id, String name, Integer semester, String description, ArrayList<Duty> duties,ArrayList<ProfessorRole>  professorRole) {
		
		super();
		this.id = id;
		this.name = name;
		this.semester = semester;
		this.description = description;
		this.duties = duties;
		this.professorRole = professorRole;
		
	}

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

	public ArrayList<Duty> getDuties() {
		return duties;
	}

	public void setDuties(ArrayList<Duty> duties) {
		this.duties = duties;
	}

	public ArrayList<ProfessorRole> getProfessorRole() {
		return professorRole;
	}

	public void setProfessorRole(ArrayList<ProfessorRole> professorRole) {
		this.professorRole = professorRole;
	}
	

}
