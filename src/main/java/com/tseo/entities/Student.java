package com.tseo.entities;

import java.util.Date;

public class Student extends User{
	
	private String gendre;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	
	public Student(){}
	public Student(String gendre, Date dateOfBirth, String address, String jMBG, String studentId) {
		super();
		this.gendre = gendre;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		JMBG = jMBG;
		this.studentId = studentId;
	}
	public String getGendre() {
		return gendre;
	}
	public void setGendre(String gendre) {
		this.gendre = gendre;
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
