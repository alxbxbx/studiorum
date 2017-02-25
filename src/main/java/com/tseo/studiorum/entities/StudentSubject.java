package com.tseo.studiorum.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class StudentSubject {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Student student;
	
	@OneToOne
	private Subject subject;
	
	private boolean available;
	
	private boolean pass;
	
	public StudentSubject() { }

	public StudentSubject(Integer id, Student student, Subject subject, boolean available, boolean pass) {
		super();
		this.id = id;
		this.student = student;
		this.subject = subject;
		this.available = available;
		this.pass = pass;
	}
	
	public StudentSubject(Student student, Subject subject, boolean available, boolean pass) {
		super();
		this.student = student;
		this.subject = subject;
		this.available = available;
		this.pass = pass;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
	
	
}
