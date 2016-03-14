package com.tseo.entities;

public class Payment {
	
	private Integer id;
	private String purpose;
	private String bankAcc;
	private double price;
	private String to;
	private String payer;
	
	
	public Payment(){
		
	}
	
	public Payment(Integer id, String purpose, String bankAcc, double price,
			String to, String payer) {
		super();
		this.id = id;
		this.purpose = purpose;
		this.bankAcc = bankAcc;
		this.price = price;
		this.to = to;
		this.payer = payer;
	}

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

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}
	

}
