package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "moviecopy")
public class MovieCopy {

	private int code;
	private Movie movie;
	
	@Id
	@Generated(GenerationTime.ALWAYS)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "moviecopyid")
	public int getCode() { return code; }
	
	@ManyToOne
	@JoinColumn(name = "movieid")
	public Movie getMovie() { return movie; }
	
	public void setCode(int code) { this.code = code; }

	public void setMovie(Movie movie) { this.movie = movie; }

	public MovieCopy() {
		
	}
	
	public MovieCopy(Movie movie) {
		
		this.movie = movie;
	}

}
