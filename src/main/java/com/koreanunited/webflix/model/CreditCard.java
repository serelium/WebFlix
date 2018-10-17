package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "creditcard")
public class CreditCard {

	private CreditCardType type;
	private String number;
	private int expiryMonth;
	private int expiryYear;
	

	@Id
	@Column(name = "CreditCardID")
	public String getNumber() { return number; }

	@ManyToOne
	@JoinColumn(name = "CreditCardTypeID", nullable = false)
	public CreditCardType getType() { return type; }
	
	@Column(name = "ExpiryMonth", nullable = false)
	public int getExpiryMonth() { return expiryMonth; }

	@Column(name = "ExpiryYear", nullable = false)
	public int getExpiryYear() { return expiryYear; }

	public void setType(CreditCardType type) { this.type = type; }

	public void setNumber(String number) { this.number = number; }

	public void setExpiryMonth(int expiryMonth) { this.expiryMonth = expiryMonth; }

	public void setExpiryYear(int expiryYear) { this.expiryYear = expiryYear; }

	public CreditCard(CreditCardType type, String number, int expiryMonth, int expiryYear) {

		this.type = type;
		this.number = number;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}
}
