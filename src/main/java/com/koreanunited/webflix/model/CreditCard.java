package com.koreanunited.webflix.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "creditcard")
public class CreditCard {

	private CreditCardType type;
	private String number;
	private int expiryMonth;
	private int expiryYear;

	@Id
	@Column(name = "creditcardid")
	public String getNumber() { return number; }

	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JoinColumn(name = "creditcardtypeid")
	public CreditCardType getType() { return type; }
	
	@Column(name = "expirymonth", nullable = false)
	public int getExpiryMonth() { return expiryMonth; }

	@Column(name = "expiryyear", nullable = false)
	public int getExpiryYear() { return expiryYear; }

	public void setType(CreditCardType type) { this.type = type; }

	public void setNumber(String number) { this.number = number; }

	public void setExpiryMonth(int expiryMonth) { this.expiryMonth = expiryMonth; }

	public void setExpiryYear(int expiryYear) { this.expiryYear = expiryYear; }

	public CreditCard() {
		
	}

	public CreditCard(CreditCardType type, String number, int expiryMonth, int expiryYear) {

		this.type = type;
		this.number = number;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}
}
