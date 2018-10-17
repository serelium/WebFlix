package com.koreanunited.webflix.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Rent")
public class Rent {

	private int id;
	private Customer customer;
	private MovieCopy movieCopy;
	private Date rentTime;
	
	@Id
	@Column(name = "RentID")
	public int getId() { return id; }
	
	@ManyToOne
	@JoinColumn(name = "UserID")
	public Customer getCustomer() { return customer; }
	
	@OneToOne
	@JoinColumn(name = "MovieCopyID")
	public MovieCopy getMovieCopy() { return movieCopy; }

	@Column(name = "RentTime")
	public Date getRentTime() {	return rentTime; }
	
	public void setId(int id) { this.id = id; }

	public void setCustomer(Customer customer) { this.customer = customer; }

	public void setMovieCopy(MovieCopy movieCopy) { this.movieCopy = movieCopy; }

	public void setRentTime(Date rentTime) { this.rentTime = rentTime; }

	public Rent(Customer customer, MovieCopy movieCopy, Date rentTime){
		
		this.customer = customer;
		this.movieCopy = movieCopy;
		this.rentTime = rentTime;
	}
}
