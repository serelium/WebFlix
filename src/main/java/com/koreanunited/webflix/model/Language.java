package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DomainLanguage")
public class Language {

	private String name;
	
	@Id
	@Column(name = "LanguageName")
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public Language(String name) {
		
		this.name = name;
	}
}
