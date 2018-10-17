package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Trailer")
public class Trailer {

	private int id;
	private String trailerLink;
	
	@Id
	@Column(name = "TrailerID")
	public int getId() { return id; }
	
	@Column(name = "TrailerLink")
	public String getTrailerLink() { return trailerLink; }
	
	public void setId(int id) { this.id = id; }

	public void setTrailerLink(String trailerLink) { this.trailerLink = trailerLink; }

	public Trailer(String trailerLink) {
		
		this.trailerLink = trailerLink;
	}
}
