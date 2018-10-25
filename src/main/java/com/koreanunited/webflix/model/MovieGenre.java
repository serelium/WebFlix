package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "domainmoviegenre")
public class MovieGenre {

	private String name;
	
	@Id
	@Column(name = "moviegenrename")
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public MovieGenre() {
		
	}
	
	public MovieGenre(String name) {
		
		this.name = name;
	}
}
