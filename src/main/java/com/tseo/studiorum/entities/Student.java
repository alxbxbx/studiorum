package com.tseo.studiorum.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Student extends User{
	
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String studentId;
	
	@ManyToMany(targetEntity=Subject.class, fetch = FetchType.LAZY)
	private Set<Subject> subjects = new HashSet<Subject>();
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Document> documents = new HashSet<Document>();
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Payment> payments = new HashSet<Payment>();
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Exam> exams = new HashSet<Exam>();
	
	
	public Student(){}

	public Student(String gender, Date dateOfBirth, String address, String jMBG, String studentId,
			Set<Subject> subjects, Set<Document> documents, Set<Payment> payments, Set<Exam> exams) {
		super();
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		JMBG = jMBG;
		this.studentId = studentId;
		this.subjects = subjects;
		this.documents = documents;
		this.payments = payments;
		this.exams = exams;
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




	public Set<Subject> getSubjects() {
		return subjects;
	}




	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}




	public Set<Document> getDocuments() {
		return documents;
	}




	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}




	public Set<Payment> getPayments() {
		return payments;
	}




	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}




	public Set<Exam> getExams() {
		return exams;
	}




	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
	
	
	
}
