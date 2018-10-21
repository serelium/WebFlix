package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	private int id;
	private int civicNumber;
	private String street;
	private String city;
	private String province;
	private String postalCode;
	
	@Id
	@Column(name = "addressid")
	public int getId() { return id; }
	
	@Column(name = "civicnumber", nullable = false)
	public int getCivicNumber() { return civicNumber;	}

	@Column(name = "street", nullable = false)
	public String getStreet() {	return street; }

	@Column(name = "city", nullable = false)
	public String getCity() { return city; }

	@Column(name = "province", nullable = false)
	public String getProvince() { return province; }

	@Column(name = "postalcode", nullable = false)
	public String getPostalCode() { return postalCode; }

	public void setId(int id) { this.id = id; }

	public void setCivicNumber(int civicNumber) { this.civicNumber = civicNumber; }

	public void setStreet(String street) { this.street = street; }

	public void setCity(String city) { this.city = city; }

	public void setProvince(String province) { this.province = province; }

	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

	
	public Address(){
		
	}
	
	public Address(int civicNumber, String street, String city, String province, String postalCode){
		
		this.civicNumber =  civicNumber;
		this.city = city;
		this.street = street;
		this.province = province;
		this.postalCode = postalCode;
	}
}
