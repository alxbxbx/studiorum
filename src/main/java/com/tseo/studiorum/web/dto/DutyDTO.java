package com.tseo.studiorum.web.dto;

import java.util.Date;

import com.tseo.studiorum.entities.Duty;

public class DutyDTO {
	
	private Integer id;
	private String typeOfDuty;
	private Date date;
	private Integer maxPoints;
	private SubjectDTO subjectDTO;
	
	public DutyDTO(Duty duty){
		this.id = duty.getId();
		this.typeOfDuty = duty.getTypeOfDuty();
		this.date = duty.getDate();
		this.maxPoints = duty.getMaxPoints();
		this.subjectDTO = new SubjectDTO(duty.getSubject());
	}
	public DutyDTO(){}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getMaxPoints() {
		return maxPoints;
	}
	public void setMaxPoints(Integer maxPoints) {
		this.maxPoints = maxPoints;
	}
	public SubjectDTO getSubjectDTO() {
		return subjectDTO;
	}
	public void setSubjectDTO(SubjectDTO subjectDTO) {
		this.subjectDTO = subjectDTO;
	}
	
	
	
	
}
