package com.koreanunited.webflix.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

	private int id;
	private String title;
	private int yearOfRelease;
	private List<Country> productionCountries;
	private Language originLanguage;
	private int length;
	private List<MovieGenre> genres;
	private List<Scriptwriter> scriptwriters;
	private List<MovieRole> artists;
	private List<Trailer> trailers;
	private String posterLink;
	private String synopsis;
	
	@Id
	@Column(name = "movieid")
	public int getId() { return id; }
	
	@Column(name = "title")
	public String getTitle() { return title; }

	@Column(name = "yearofrelease")
	public int getYearOfRelease() {	return yearOfRelease; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="movie_countries",
			   joinColumns = @JoinColumn(name="movieid", referencedColumnName="movieid"),
			   inverseJoinColumns = @JoinColumn(name="country", referencedColumnName="countryname"))
	public List<Country> getProductionCountries() { return productionCountries; }

	@ManyToOne
	@JoinColumn(name = "originlanguage" , referencedColumnName="languagename")
	public Language getOriginLanguage() { return originLanguage; }

	@Column(name = "movielength")
	public int getLength() { return length; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="moviegenre",
			   joinColumns = @JoinColumn(name="movieid", referencedColumnName="movieid"),
			   inverseJoinColumns = @JoinColumn(name="moviegenrename", referencedColumnName="moviegenrename"))
	public List<MovieGenre> getGenres() { return genres; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="movie_scriptwriters",
			   joinColumns = @JoinColumn(name="movieid", referencedColumnName="movieid"),
			   inverseJoinColumns = @JoinColumn(name="scriptwritername", referencedColumnName="fullname"))
	public List<Scriptwriter> getScriptwriters() { return scriptwriters; }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	public List<MovieRole> getArtists() { return artists; }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	public List<Trailer> getTrailers() { return trailers; }

	@Column(name = "posterlink")
	public String getPosterLink() { return posterLink; }

	@Column(name = "synopsis")
	public String getSynopsis() { return synopsis; }
	
	public void setId(int id) { this.id = id; }

	public void setTitle(String title) { this.title = title; }

	public void setYearOfRelease(int yearOfRelease) { this.yearOfRelease = yearOfRelease; }

	public void setProductionCountries(List<Country> productionCountries) { this.productionCountries = productionCountries; }

	public void setOriginLanguage(Language originLanguage) { this.originLanguage = originLanguage; }

	public void setLength(int length) { this.length = length; }

	public void setGenres(List<MovieGenre> genres) { this.genres = genres; }

	public void setScriptwriters(List<Scriptwriter> scriptwriters) { this.scriptwriters = scriptwriters; }

	public void setArtists(List<MovieRole> artists) { this.artists = artists; }

	public void setTrailers(List<Trailer> trailers) { this.trailers = trailers; }

	public void setPosterLink(String posterLink) { this.posterLink = posterLink; }

	public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
	
	public Movie() {
		
	}

	public Movie(int id,
			 String title,
			 int yearOfRelease,
			 List<Country> productionCountries,
			 Language originLanguage,
			 int length,
			 List<MovieGenre> genres,
			 List<Scriptwriter> scriptwriters,
			 List<MovieRole> cast,
			 List<Trailer> trailers,
			 String posterLink,
			 String synopsis) {
				
				this.id = id;
				this.title = title;
				this.yearOfRelease = yearOfRelease;
				this.productionCountries = productionCountries;
				this.originLanguage = originLanguage;
				this.length = length;
				this.genres = genres;
				this.scriptwriters = scriptwriters;
				this.artists = cast;
				this.trailers = trailers;
				this.posterLink = posterLink;
				this.synopsis = synopsis;
			}
}
