package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "MovieRole")
public class MovieRole {

	private int id;
	private MovieRoleType type;
	private String characterName;
	private Artist artist;
	
	@Id
	@Column(name = "MovieRoleID")
	public int getId() { return id; }
	
	@ManyToOne
	@JoinColumn(name = "RoleType", nullable = false)
	public MovieRoleType getType() { return type; }
	
	@Column(name = "CharacterName")
	public String getCharacterName() { return characterName; }
	
	@ManyToOne
	@JoinColumn(name = "ArtistID", nullable = false)
	public Artist getArtist() {	return artist; }

	public void setId(int id) { this.id = id; }

	public void setType(MovieRoleType type) { this.type = type; }

	public void setCharacterName(String characterName) { this.characterName = characterName; }

	public void setArtist(Artist artist) { this.artist = artist; }

	public MovieRole(MovieRoleType type, String characterName, Artist artist){
		
		this.type = type;
		this.characterName = characterName;
		this.artist = artist;
	}
}
