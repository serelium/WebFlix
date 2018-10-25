package com.koreanunited.webflix.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "userid")
public class Customer extends Person{

	private SubscriptionPlan subscriptionPlan;
	private CreditCard creditCard;
	private List<Rent> rents;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JoinColumn(name= "subscriptionplanid")
	public SubscriptionPlan getSubscription() { return subscriptionPlan; }

	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JoinColumn(name= "creditcardid")
	public CreditCard getCreditCard() { return creditCard; }

	@OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JoinColumn(name= "rentid")
	public List<Rent> getRents() { return rents; }
	
	public void setSubscription(SubscriptionPlan subscription) { this.subscriptionPlan = subscription; }

	public void setCreditCard(CreditCard creditCard) { this.creditCard = creditCard; }

	public void setRents(List<Rent> rents) { this.rents = rents; }

	
	public Customer(){
		super();
	}
	
	public Customer(int userId, String firstName, String lastName, String email, Address address, Date birthDate, String phoneNumber, String password, SubscriptionPlan subscription, CreditCard creditCard){
		super(userId, firstName, lastName, email, address, birthDate, phoneNumber, password);
		
		this.subscriptionPlan = subscription;
		this.creditCard = creditCard;
	}

}
