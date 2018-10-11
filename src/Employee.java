
public class Employee {

	private Person person;
	private int regristrationNumber;
	
	public Employee(Person person, int regristrationNumber) {
		
		this.regristrationNumber = regristrationNumber;
	}

	public Person getPerson() {
		return person;
	}

	public int getRegristrationNumber() {
		return regristrationNumber;
	}
}
