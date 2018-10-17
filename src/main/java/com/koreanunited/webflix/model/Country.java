package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Country")
public class Country {

	private String name;
	
	@Id
	@Column(name = "CountryName")
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public Country(String name){
		
		this.name = name;
	}

}
