
public class CreditCard {

	private CreditCardType type;
	private String number;
	private int expiryMonth;
	private int expiryYear;
	private int cvv;
	
	public CreditCard(CreditCardType type, String number, int expiryMonth, int expiryYear) {

		this.type = type;
		this.number = number;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}
	
	public CreditCardType getType() {
		return type;
	}

	public void setType(CreditCardType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public enum CreditCardType{
		
		Visa,
		MasterCard,
		YeOldCard,
	}
}
