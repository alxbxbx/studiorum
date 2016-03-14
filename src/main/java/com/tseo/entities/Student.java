package com.tseo.entities;

import java.util.Date;
import java.util.List;

public class Student extends User{
	
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	private List<Subject> subjects;
	private List<Document> documents;
	private List<Payment> payments;
	
	
	public Student(){}
	public Student(String gender, Date dateOfBirth, String address, String jMBG, 
			String studentId, List<Subject> subjects, List<Document> documents, List<Payment> payments) {

		super();
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		JMBG = jMBG;
		this.studentId = studentId;
		this.subjects = subjects;
		this.documents = documents;
		this.payments = payments;
		
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
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
}
