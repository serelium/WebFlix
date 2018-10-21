package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "domainlanguage")
public class Language {

	@Id
	@Column(name = "languagename")
	private String name;
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public Language() {
		
	}
	
	public Language(String name) {
		
		this.name = name;
	}
}
