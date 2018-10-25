package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "creditcardtype")
public class CreditCardType {

	private int id;
	private ECreditCardType type;
	
	@Id
	@Generated(GenerationTime.ALWAYS)
	@Column(name = "creditCardtypeid")
	public int getId() { return id; }
	
	@Enumerated(EnumType.STRING)
	@Column(name = "typename")
	public ECreditCardType getTypeName() { return type; }
	
	public void setId(int id) { this.id = id; }

	public void setTypeName(ECreditCardType type) { this.type = type; }

	public CreditCardType() {
		
	}
	
	public CreditCardType(ECreditCardType type) {
		
		this.type = type;
	}
	
	public enum ECreditCardType{
		
		Visa,
		MasterCard
	}
}
