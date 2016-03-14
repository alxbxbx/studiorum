package com.tseo.entities;

import java.util.Date;

public class Professor extends User{
	
	
	private String gendre;
	private Date dateOfBirth;
	private String address;
	private String JMBG;
	private String title;
	
	
	public Professor(){}
	public Professor(String gendre, Date dateOfBirth, String address, String jMBG, String title) {
		super();
		this.gendre = gendre;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		JMBG = jMBG;
		this.title = title;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
