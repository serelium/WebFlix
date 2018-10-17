package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MovieCopy")
public class MovieCopy {

	private int code;
	private Movie movie;
	
	@Id
	@Column(name = "MovieCopyID")
	public int getCode() { return code; }
	
	@ManyToOne
	@JoinColumn(name = "MovieID")
	public Movie getMovie() { return movie; }
	
	public void setCode(int code) { this.code = code; }

	public void setMovie(Movie movie) { this.movie = movie; }

	public MovieCopy(int code, Movie movie) {
		
		this.code = code;
		this.movie = movie;
	}

}
