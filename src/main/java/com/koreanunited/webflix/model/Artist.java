package com.koreanunited.webflix.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {

	private int id;
	private String name;
	private Date birthDate;
	private String birthPlace;
	private String pictureLink;
	private String biography;
	

	@Id
	@Column(name = "artistid")
	public int getId() { return id; }

	@Column(name = "artistname", nullable = false)
	public String getName() { return name; }

	@Column(name = "birthdate")
	public Date getBirthDate() { return birthDate; }

	@Column(name = "birthplace")
	public String getBirthPlace() {	return birthPlace; }

	@Column(name = "picturelink")
	public String getPictureLink() { return pictureLink; }

	@Column(name = "biography")
	public String getBiography() { return biography; }
	
	public void setId(int id) { this.id = id; }

	public void setName(String name) { this.name = name; }

	public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

	public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }

	public void setPictureLink(String pictureLink) { this.pictureLink = pictureLink; }

	public void setBiography(String biography) { this.biography = biography; }

	
	public Artist() {
		
	}
	
	public Artist(int id, String name, Date birthDate, String birthPlace, String pictureLink, String biography) {
		
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
		this.pictureLink = pictureLink;
		this.biography = biography;
	}
}
