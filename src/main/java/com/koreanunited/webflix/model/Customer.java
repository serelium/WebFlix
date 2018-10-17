package com.koreanunited.webflix.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends Person{

	private SubscriptionPlan subscription;
	private CreditCard creditCard;
	private ArrayList<Rent> rents;
	
	@ManyToOne
	@JoinColumn(name = "SubscriptionPlanID")
	public SubscriptionPlan getSubscription() { return subscription; }

	@OneToOne
	@JoinColumn(name = "CreditCardID")
	public CreditCard getCreditCard() { return creditCard; }

	@OneToMany
	public List<Rent> getRents() { return rents; }
	
	public void setSubscription(SubscriptionPlan subscription) { this.subscription = subscription; }

	public void setCreditCard(CreditCard creditCard) { this.creditCard = creditCard; }

	public void setRents(ArrayList<Rent> rents) { this.rents = rents; }

	public Customer(int userId, String firstName, String lastName, String email, Address address, Date birthDate, String phoneNumber, String password, SubscriptionPlan subscription, CreditCard creditCard){
		super(userId, firstName, lastName, email, address, birthDate, phoneNumber, password);
		
		this.subscription = subscription;
		this.creditCard = creditCard;
	}

}
