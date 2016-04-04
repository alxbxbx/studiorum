package com.tseo.studiorum.web.dto;

import java.util.Date;

import com.tseo.studiorum.entities.Student;

public class StudentDTO {
	
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	
	public StudentDTO(Student student){
		this.gender = student.getGender();
		this.dateOfBirth = student.getDateOfBirth();
		this.address = student.getAddress();
		this.JMBG = student.getJMBG();
		this.studentId = student.getStudentId();
	}
	public StudentDTO(){}
	
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
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	
	

}
