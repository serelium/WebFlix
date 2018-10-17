package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DomainMovieRole")
public class MovieRoleType {

	private EMovieRoleType type;
	
	@Id
	@Column(name = "MovieRole")
	public EMovieRoleType getTypeName() { return type; }
	
	public void setTypeName(EMovieRoleType type) { this.type = type; }

	public MovieRoleType(EMovieRoleType type) {
		
		this.type = type;
	}
	
	public enum EMovieRoleType{
		
		Director,
		Actor
	}
}
