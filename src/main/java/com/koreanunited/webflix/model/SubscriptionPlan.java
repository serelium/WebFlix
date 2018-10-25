package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name ="subscriptionplan")
public class SubscriptionPlan {

	private int id;
	private SubscriptionPlanType type;
	private double montlyCost;
	private int maxRent;
	private int maxRentDuration;
	
	
	@Id
	@Generated(GenerationTime.ALWAYS)
	@Column(name = "subscriptionplanid")
	public int getId() { return id; }
	
	@Enumerated(EnumType.STRING)
	@Column(name = "planname")
	public SubscriptionPlanType getType() { return type; }

	@Column(name = "monthlycost")
	public double getMontlyCost() {	return montlyCost; }

	@Column(name = "maxrent")
	public int getMaxRent() { return maxRent; }

	@Column(name = "maxrentduration")
	public int getMaxRentDuration() { return maxRentDuration; }

	public void setId(int id) { this.id = id; }

	public void setType(SubscriptionPlanType type) { this.type = type; }

	public void setMontlyCost(double montlyCost) { this.montlyCost = montlyCost; }

	public void setMaxRent(int maxRent) { this.maxRent = maxRent; }

	public void setMaxRentDuration(int maxRentDuration) { this.maxRentDuration = maxRentDuration; }

	public enum SubscriptionPlanType {
		
		Beginner,
		Intermediate,
		Advanced
	}
	
	public SubscriptionPlan() {
		
	}
	
	public SubscriptionPlan(SubscriptionPlanType type, double montlyCost, int maxRent, int maxRentDuration) {
		
		this.type = type;
		this.montlyCost = montlyCost;
		this.maxRent = maxRent;
		this.maxRentDuration = maxRentDuration;
	}
}
