package com.tseo.studiorum.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Duty {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String typeOfDuty; // usmeni ispit, kolokvijum, test...
	
	private Integer maxPoints;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Subject subject;

	
	public Duty(){
		
	}
	
	
	public Duty(Integer id, String typeOfDuty, Integer maxPoints, Subject subject) {
		super();
		this.id = id;
		this.typeOfDuty = typeOfDuty;
		this.maxPoints = maxPoints;
		this.subject = subject;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeOfDuty() {
		return typeOfDuty;
	}

	public void setTypeOfDuty(String typeOfDuty) {
		this.typeOfDuty = typeOfDuty;
	}

	public Integer getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(Integer maxPoints) {
		this.maxPoints = maxPoints;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	
}
