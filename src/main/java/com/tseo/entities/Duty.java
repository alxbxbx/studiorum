package com.tseo.entities;

import java.util.Date;

public class Duty {

	private Integer id;
	private String typeOfDuty; // usmeni ispit, kolokvijum, test...
	private Integer points;
	private Integer maxPoints;
	private Date date;
	private Boolean finished;
	
	public Duty(){
		
	}
	
	public Duty(Integer id, String typeOfDuty, Integer points,
			Integer maxPoints, Date date, Boolean finished) {
		super();
		this.id = id;
		this.typeOfDuty = typeOfDuty;
		this.points = points;
		this.maxPoints = maxPoints;
		this.date = date;
		this.finished = finished;
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(Integer maxPoints) {
		this.maxPoints = maxPoints;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
}
