package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scriptwriter")
public class Scriptwriter {

	private String name;
	
	@Id
	@Column(name = "fullname")
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public Scriptwriter() {
		
	}
	
	public Scriptwriter(String name) {
		
		this.name = name;
	}
}
