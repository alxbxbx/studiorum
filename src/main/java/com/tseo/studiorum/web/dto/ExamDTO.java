package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.Exam;

public class ExamDTO {
	
	private Integer id;
	private Integer points;
	private Boolean pass;
	private DutyDTO dutyDTO;
	private StudentDTO studentDTO;
	
	public ExamDTO(Exam exam){
		this.id = exam.getId();
		this.points = exam.getPoints();
		this.pass = exam.getPass();
		this.dutyDTO = new DutyDTO(exam.getDuty());
		this.studentDTO = new StudentDTO(exam.getStudent());
	}
	public ExamDTO(){}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Boolean getPass() {
		return pass;
	}
	public void setPass(Boolean pass) {
		this.pass = pass;
	}
	public DutyDTO getDutyDTO() {
		return dutyDTO;
	}
	public void setDutyDTO(DutyDTO dutyDTO) {
		this.dutyDTO = dutyDTO;
	}
	public StudentDTO getStudentDTO() {
		return studentDTO;
	}
	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}
	
	

}
