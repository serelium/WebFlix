package com.koreanunited.webflix.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "userid")
public class Employee extends Person{

	private int regristrationNumber;
	
	@Column(name = "regeristrationid", nullable = false)
	public int getRegristrationNumber() { return regristrationNumber; }
	
	public void setRegristrationNumber(int regristrationNumber) { this.regristrationNumber = regristrationNumber; }

	public Employee() {
		super();
	}
	
	public Employee(int userId, String firstName, String lastName, String email, Address address, Date birthDate, String phoneNumber, String password, int regristrationNumber) {
		super(userId, firstName, lastName, email, address, birthDate, phoneNumber, password);
		
		this.regristrationNumber = regristrationNumber; 
	}
}
