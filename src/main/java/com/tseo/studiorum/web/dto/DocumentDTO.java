package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.Document;

public class DocumentDTO {
	
	private Integer id;
	private String name;
	private String path;
	private StudentDTO studentDTO;
	
	public DocumentDTO(Document document){
		this.id = document.getId();
		this.name = document.getName();
		this.path = document.getPath();
		this.studentDTO = new StudentDTO(document.getStudent());
	}
	public DocumentDTO(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public StudentDTO getStudentDTO() {
		return studentDTO;
	}
	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}
	
	
	
}
