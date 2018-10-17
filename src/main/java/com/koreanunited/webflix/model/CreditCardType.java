package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "CreditCardType")
public class CreditCardType {

	private int id;
	private ECreditCardType type;
	
	@Id
	@Generated(GenerationTime.ALWAYS)
	@Column(name = "CreditCardTypeID")
	public int getId() { return id; }
	
	@Column(name = "TypeName")
	public ECreditCardType getTypeName() { return type; }
	
	public void setId(int id) { this.id = id; }

	public void setTypeName(ECreditCardType type) { this.type = type; }

	public CreditCardType(ECreditCardType type) {
		
		this.type = type;
	}
	
	public enum ECreditCardType{
		
		Visa,
		MasterCard
	}
}
