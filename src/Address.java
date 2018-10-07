
public class Address {

	private int civicNumber;
	private String street;
	private String city;
	private String province;
	private String postalCode;
	
	public int getCivicNumber() {
		return civicNumber;
	}

	public void setCivicNumber(int civicNumber) {
		this.civicNumber = civicNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Address(int civicNumber, String street, String city, String province, String postalCode){
		
		this.civicNumber =  civicNumber;
		this.city = city;
		this.street = street;
		this.province = province;
		this.postalCode = postalCode;
	}
}
