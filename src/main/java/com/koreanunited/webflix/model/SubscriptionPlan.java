package com.koreanunited.webflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="SubscriptionPlan")
public class SubscriptionPlan {

	private int id;
	private String type;
	private double montlyCost;
	private int maxRent;
	private int maxRentDuration;
	
	public SubscriptionPlan(SubscriptionPlanType type, double montlyCost, int maxRent, int maxRentDuration) {
		
		this.type = type.name();
		this.montlyCost = montlyCost;
		this.maxRent = maxRent;
		this.maxRentDuration = maxRentDuration;
	}
	
	@Id
	@Column(name = "SubscriptionPlanID")
	public int getId() { return id; }
	
	@Column(name = "PlanName")
	public String getType() { return type; }

	@Column(name = "MonthlyCost")
	public double getMontlyCost() {	return montlyCost; }

	@Column(name = "MaxRent")
	public int getMaxRent() { return maxRent; }

	@Column(name = "MaxRentDuration")
	public int getMaxRentDuration() { return maxRentDuration; }

	public void setId(int id) { this.id = id; }

	public void setType(String type) { this.type = type; }

	public void setMontlyCost(double montlyCost) { this.montlyCost = montlyCost; }

	public void setMaxRent(int maxRent) { this.maxRent = maxRent; }

	public void setMaxRentDuration(int maxRentDuration) { this.maxRentDuration = maxRentDuration; }

	public enum SubscriptionPlanType {
		
		Beginner,
		Intermediate,
		Advanced
	}
}
