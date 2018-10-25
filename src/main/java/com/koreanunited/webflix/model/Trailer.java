package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trailer")
public class Trailer {

	private int id;
	private String trailerLink;
	private Movie movie;
	
	@Id
	@Column(name = "trailerid")
	public int getId() { return id; }
	
	@Column(name = "trailerlink")
	public String getTrailerLink() { return trailerLink; }
	
	@ManyToOne
	@JoinColumn(name = "movieid")
	public Movie getMovie() { return movie; };
	
	public void setId(int id) { this.id = id; }

	public void setTrailerLink(String trailerLink) { this.trailerLink = trailerLink; }
	
	public void setMovie(Movie movie) { this.movie = movie; }

	public Trailer() {
		
	}
	
	public Trailer(String trailerLink) {
		
		this.trailerLink = trailerLink;
	}
}
