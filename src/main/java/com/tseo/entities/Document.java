package com.tseo.entities;

public class Document {
	
	private Integer id;
	private String name;
	private String path;
	
	public Document(){
		
	}
	
	public Document(Integer id, String name, String path) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
	}

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
	
}
