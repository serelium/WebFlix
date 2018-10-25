package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "domainmovierole")
public class MovieRoleType {

	private EMovieRoleType type;
	
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "movierole")
	public EMovieRoleType getTypeName() { return type; }
	
	public void setTypeName(EMovieRoleType type) { this.type = type; }

	
	public MovieRoleType() {
		
	}
	
	public MovieRoleType(EMovieRoleType type) {
		
		this.type = type;
	}
	
	public enum EMovieRoleType{
		
		Director,
		Actor
	}
}
