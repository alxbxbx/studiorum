package com.tseo.studiorum.web.dto;

import java.util.Date;

import com.tseo.studiorum.entities.Professor;

public class ProfessorDTO {
	
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String title;
	
	public ProfessorDTO(Professor professor){
		this.gender = professor.getGender();
		this.dateOfBirth = professor.getDateOfBirth();
		this.address = professor.getAddress();
		this.JMBG = professor.getJMBG();
		this.title = professor.getTitle();
	}
	
	public ProfessorDTO(){}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJMBG() {
		return JMBG;
	}
	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
