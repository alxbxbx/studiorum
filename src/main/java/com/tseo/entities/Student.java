package com.tseo.entities;

import java.util.ArrayList;
import java.util.Date;

public class Student extends User{
	
	private String gendre;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	private ArrayList<Subject> subjects;
	private ArrayList<Document> documents;
	private ArrayList<Payment> payments;
	
	
	public Student(){}
	public Student(String gendre, Date dateOfBirth, String address, String jMBG, 
			String studentId, ArrayList<Subject> subjects, ArrayList<Document> documents, ArrayList<Payment> payments) {
		super();
		this.gendre = gendre;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		JMBG = jMBG;
		this.studentId = studentId;
		this.subjects = subjects;
		this.documents = documents;
		this.payments = payments;
		
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
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}
	public ArrayList<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
	public ArrayList<Payment> getPayments() {
		return payments;
	}
	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
	
	
}
