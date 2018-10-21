package com.koreanunited.webflix.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Movie")
public class Movie {

	private int id;
	private String title;
	private int yearOfRelease;
	private ArrayList<Country> productionCountries;
	private Language originLanguage;
	private int length;
	private ArrayList<MovieGenre> genres;
	private MovieRole director;
	private ArrayList<Scriptwriter> scriptwriters;
	private ArrayList<MovieRole> cast;
	private ArrayList<Trailer> trailers;
	private String posterLink;
	private String synopsis;
	
	@Id
	@Column(name = "MovieID")
	public int getId() { return id; }
	
	@Column(name = "Title")
	public String getTitle() { return title; }

	@Column(name = "YearOfRelease")
	public int getYearOfRelease() {	return yearOfRelease; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="Movie_Countries",
			   joinColumns = @JoinColumn(name="MovieID", referencedColumnName="MovieID"),
			   inverseJoinColumns = @JoinColumn(name="Country", referencedColumnName="CountryName"))
	public List<Country> getProductionCountries() { return productionCountries; }

	@ManyToOne
	@JoinColumn(name = "LanguageName")
	public Language getOriginLanguage() { return originLanguage; }

	@Column(name = "MovieLength")
	public int getLength() { return length; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="MovieGenre",
			   joinColumns = @JoinColumn(name="MovieID", referencedColumnName="MovieID"),
			   inverseJoinColumns = @JoinColumn(name="MovieGenreName", referencedColumnName="MovieGenreName"))
	public List<MovieGenre> getGenres() { return genres; }

	@OneToOne
	public MovieRole getDirector() { return director; }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			   name="Movie_Scriptwriters",
			   joinColumns = @JoinColumn(name="MovieID", referencedColumnName="MovieID"),
			   inverseJoinColumns = @JoinColumn(name="ScriptwriterName", referencedColumnName="FullName"))
	public List<Scriptwriter> getScriptwriters() { return scriptwriters; }

	@OneToMany
	public List<MovieRole> getCast() { return cast; }

	@OneToMany
	public List<Trailer> getTrailers() { return trailers; }

	@Column(name = "PosterLink")
	public String getPosterLink() { return posterLink; }

	@Column(name = "Synopsis")
	public String getSynopsis() { return synopsis; }
	
	public void setId(int id) { this.id = id; }

	public void setTitle(String title) { this.title = title; }

	public void setYearOfRelease(int yearOfRelease) { this.yearOfRelease = yearOfRelease; }

	public void setProductionCountries(ArrayList<Country> productionCountries) { this.productionCountries = productionCountries; }

	public void setOriginLanguage(Language originLanguage) { this.originLanguage = originLanguage; }

	public void setLength(int length) { this.length = length; }

	public void setGenres(ArrayList<MovieGenre> genres) { this.genres = genres; }

	public void setDirector(MovieRole director) { this.director = director; }

	public void setScriptwriters(ArrayList<Scriptwriter> scriptwriters) { this.scriptwriters = scriptwriters; }

	public void setCast(ArrayList<MovieRole> cast) { this.cast = cast; }

	public void setTrailers(ArrayList<Trailer> trailers) { this.trailers = trailers; }

	public void setPosterLink(String posterLink) { this.posterLink = posterLink; }

	public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
	
	

	public Movie(int id,
			 String title,
			 int yearOfRelease,
			 ArrayList<Country> productionCountries,
			 Language originLanguage,
			 int length,
			 ArrayList<MovieGenre> genres,
			 MovieRole director,
			 ArrayList<Scriptwriter> scriptwriters,
			 ArrayList<MovieRole> cast,
			 ArrayList<Trailer> trailers,
			 String posterLink,
			 String synopsis) {
				
				this.id = id;
				this.title = title;
				this.yearOfRelease = yearOfRelease;
				this.productionCountries = productionCountries;
				this.originLanguage = originLanguage;
				this.length = length;
				this.genres = genres;
				this.director = director;
				this.scriptwriters = scriptwriters;
				this.cast = cast;
				this.trailers = trailers;
				this.posterLink = posterLink;
				this.synopsis = synopsis;
			}
}
