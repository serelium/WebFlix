package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "movierole")
public class MovieRole {

	private int id;
	private MovieRoleType type;
	private String characterName;
	private Artist artist;
	private Movie movie;
	
	@Id
	@Column(name = "movieroleid")
	public int getId() { return id; }
	
	@ManyToOne
	@JoinColumn(name = "roletype", nullable = false)
	public MovieRoleType getType() { return type; }
	
	@Column(name = "charactername")
	public String getCharacterName() { return characterName; }
	
	@ManyToOne
	@JoinColumn(name = "artistid", nullable = false)
	public Artist getArtist() {	return artist; }
	
	@ManyToOne
	@JoinColumn(name = "movieid")
	public Movie getMovie() { return movie; };

	public void setId(int id) { this.id = id; }

	public void setType(MovieRoleType type) { this.type = type; }

	public void setCharacterName(String characterName) { this.characterName = characterName; }

	public void setArtist(Artist artist) { this.artist = artist; }
	
	public void setMovie(Movie movie) { this.movie = movie; }

	public MovieRole() {
		
	}
	
	public MovieRole(MovieRoleType type, String characterName, Artist artist){
		
		this.type = type;
		this.characterName = characterName;
		this.artist = artist;
	}
}
