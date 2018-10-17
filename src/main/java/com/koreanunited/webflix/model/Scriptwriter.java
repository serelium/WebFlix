package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Scriptwriter")
public class Scriptwriter {

	private String name;
	
	@Id
	@Column(name = "FullName")
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public Scriptwriter(String name) {
		
		this.name = name;
	}
}
