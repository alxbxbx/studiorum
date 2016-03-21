package com.tseo.studiorum.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Subject {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private Integer semester;
	
	private String description;
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Duty> duties = new HashSet<Duty>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<ProfessorRole>  professorRole = new HashSet<ProfessorRole>();
	
	@ManyToMany(targetEntity=Student.class, mappedBy="subjects", fetch = FetchType.LAZY)
	private Set<Student> students = new HashSet<Student>();
	
	
	public Subject(){
		
	}
	

	


	public Subject(Integer id, String name, Integer semester, String description, Set<Duty> duties,
			Set<ProfessorRole> professorRole, Set<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.semester = semester;
		this.description = description;
		this.duties = duties;
		this.professorRole = professorRole;
		this.students = students;
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





	public Set<Duty> getDuties() {
		return duties;
	}





	public void setDuties(Set<Duty> duties) {
		this.duties = duties;
	}





	public Set<ProfessorRole> getProfessorRole() {
		return professorRole;
	}





	public void setProfessorRole(Set<ProfessorRole> professorRole) {
		this.professorRole = professorRole;
	}





	public Set<Student> getStudents() {
		return students;
	}





	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	
	
	

}
