package com.tseo.studiorum.web.dto;

import java.util.Date;

import com.tseo.studiorum.entities.Student;

public class StudentDTO {
	
	private Integer id;
	private String role;
	private String  name;
	private String lastName;
	private String userName;
	private String password;
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	private String picturePath;
	
	public StudentDTO(Student student){
		this.gender = student.getGender();
		this.dateOfBirth = student.getDateOfBirth();
		this.address = student.getAddress();
		this.JMBG = student.getJMBG();
		this.studentId = student.getStudentId();
		this.id = student.getId();
		this.role = student.getRole();
		this.name = student.getName();
		this.picturePath = student.getPicturePath();
		this.lastName = student.getLastName();
		this.userName = student.getUserName();
		this.password = student.getPassword();
	}
	public StudentDTO(){}
	
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
