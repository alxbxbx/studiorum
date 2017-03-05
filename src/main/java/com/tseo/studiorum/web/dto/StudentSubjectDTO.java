package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.StudentSubject;

public class StudentSubjectDTO {
	
	private Integer id;
	private StudentDTO studentDTO;
	private SubjectDTO subjectDTO;
	private boolean available;
	private boolean pass;
	
	public StudentSubjectDTO() { }
	
	public StudentSubjectDTO(StudentSubject studentSubject){
		this.id = studentSubject.getId();
		this.studentDTO = new StudentDTO(studentSubject.getStudent());
		this.subjectDTO = new SubjectDTO(studentSubject.getSubject());
		this.available = studentSubject.isAvailable();
		this.pass = studentSubject.isPass();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StudentDTO getStudentDTO() {
		return studentDTO;
	}

	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}

	public SubjectDTO getSubjectDTO() {
		return subjectDTO;
	}

	public void setSubjectDTO(SubjectDTO subjectDTO) {
		this.subjectDTO = subjectDTO;
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
