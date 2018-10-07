import java.util.ArrayList;

public class Customer {

	private Person person;
	private SubscriptionPlan subscription;
	private CreditCard creditCard;
	private ArrayList<Rent> rents;
	
	
	public Customer(Person person, SubscriptionPlan subscription, CreditCard creditCard){
		
		this.person = person;
		this.subscription = subscription;
		this.creditCard = creditCard;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public SubscriptionPlan getSubscription() {
		return subscription;
	}


	public void setSubscription(SubscriptionPlan subscription) {
		this.subscription = subscription;
	}


	public CreditCard getCreditCard() {
		return creditCard;
	}


	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	public ArrayList<Rent> getRents() {
		return rents;
	}


	public void setRents(ArrayList<Rent> rents) {
		this.rents = rents;
	}
}
