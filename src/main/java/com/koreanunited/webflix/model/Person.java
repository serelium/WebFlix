package com.koreanunited.webflix.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public abstract class Person {

	private int userId;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String email;
	private String phoneNumber;
	private String password;
	private Address address;
	
	@Id
	@Column(name = "userid")
	public int getUserId() { return userId; }

	@Column(name = "firstname", nullable = false)
	public String getFirstName() { return firstName; }

	@Column(name = "lastname", nullable = false)
	public String getLastName() { return lastName; }

	@Column(name = "email", nullable = false)
	public String getEmail() { return email; }

	@OneToOne(fetch = FetchType.LAZY)
	@Transactional
	@JoinColumn(name = "addressid")
	public Address getAddress() { return address; }

	@Column(name = "birthdate", nullable = false)
	public Date getBirthDate() { return birthDate; }

	@Column(name = "phonenumber", nullable = false)
	public String getPhoneNumber() { return phoneNumber; }

	@Column(name = "userpassword", nullable = false)
	public String getPassword() { return password; }
	
	public void setUserId(int userId) { this.userId = userId; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

	public void setEmail(String email) { this.email = email; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public void setPassword(String password) { this.password = password; }

	public void setAddress(Address address) { this.address = address; }

	public Person() {
		
	}
	
	public Person(int userId, String firstName, String lastName, String email, Address address, Date birthDate, String phoneNumber, String password) {
		
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
}
