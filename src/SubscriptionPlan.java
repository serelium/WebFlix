
public class SubscriptionPlan {

	private SubscriptionPlanType type;
	private double montlyCost;
	private int maxRent;
	private int maxRentDuration;
	
	public SubscriptionPlan(SubscriptionPlanType type, double montlyCost, int maxRent, int maxRentDuration) {
		
		this.type = type;
		this.montlyCost = montlyCost;
		this.maxRent = maxRent;
		this.maxRentDuration = maxRentDuration;
	}
	
	public SubscriptionPlanType getType() {
		return type;
	}

	public void setType(SubscriptionPlanType type) {
		this.type = type;
	}

	public double getMontlyCost() {
		return montlyCost;
	}

	public void setMontlyCost(double montlyCost) {
		this.montlyCost = montlyCost;
	}

	public int getMaxRent() {
		return maxRent;
	}

	public void setMaxRent(int maxRent) {
		this.maxRent = maxRent;
	}

	public int getMaxRentDuration() {
		return maxRentDuration;
	}

	public void setMaxRentDuration(int maxRentDuration) {
		this.maxRentDuration = maxRentDuration;
	}

	public enum SubscriptionPlanType {
		
		Beginner,
		Intermediate,
		Advanced
	}
}
