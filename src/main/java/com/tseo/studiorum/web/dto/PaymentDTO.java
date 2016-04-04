package com.tseo.studiorum.web.dto;

import com.tseo.studiorum.entities.Payment;

public class PaymentDTO {
	
	private Integer id;
	private String purpose;
	private String bankAcc;
	private double price;
	private String to;
	private StudentDTO studentDTO;
	
	public PaymentDTO(Payment payment){
		this.id = payment.getId();
		this.purpose = payment.getPurpose();
		this.bankAcc = payment.getBankAcc();
		this.price = payment.getPrice();
		this.to = payment.getTo();
		this.studentDTO = new StudentDTO(payment.getStudent());
	}
	
	public PaymentDTO(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getBankAcc() {
		return bankAcc;
	}
	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public StudentDTO getStudentDTO() {
		return studentDTO;
	}
	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}
	
	

}
