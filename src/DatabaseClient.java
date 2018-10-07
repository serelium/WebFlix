import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseClient {

	private Connection connection;
	
	public DatabaseClient(String host, String username, String password, int port, String ssid) throws SQLException {
		
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + ssid, username, password);
	}
	
	public void insertMovieRole(MovieRole mRole) {
	    
	}
	
	public int insertAddress(Address address) throws SQLException {
		 
		Statement statement = connection.createStatement();
		
		String insertAddressSql = "INSERT INTO Address (CivicNumber, Street, City, Province, PostalCode) "
				+ "VALUES (" + address.getCivicNumber() + ", '"
				+ address.getStreet() + "', '"
				+ address.getCity() + "', '"
				+ address.getProvince() + "', '"
				+ address.getPostalCode().replace(" ", "") + "')";
		
		System.out.println(insertAddressSql);
		
		statement.executeUpdate(insertAddressSql);
		
		ResultSet result = statement.executeQuery("SELECT MAX(AddressID) AS ID FROM Address");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		return insertedRowID;
	}
	
	public void insertArtist(Artist artist) {     
		
	}
	
	public void insertCountry(Country country) {
	    
	}
	
	public void insertProductionCountry(Country country, Movie movie) {
	    
	}
	
	public String insertCreditCard(CreditCard creditCard) throws SQLException {
		
		
		Statement statement = connection.createStatement();
		
		String selectCreditCardTypeID = "SELECT CreditCardTypeID FROM CreditCardType WHERE CreditCardType.TypeName LIKE '" + creditCard.getType().name() + "'";
		ResultSet result = statement.executeQuery(selectCreditCardTypeID);
		result.next();
		
		String creditCardID = creditCard.getNumber().replace(" ", "");
		String insertCreditCardSql = "INSERT INTO CreditCard (CreditCardID, CreditCardTypeID, ExpiryMonth, ExpiryYear, CVV) "
				+ "VALUES (" + creditCardID + ", "
				+ result.getInt("CreditCardTypeID") + ", "
				+ creditCard.getExpiryMonth() + ", "
				+ creditCard.getExpiryYear() + ", "
				+ creditCard.getCvv() + ")";
		
		statement.executeUpdate(insertCreditCardSql);

		result.close();
		statement.close();
		
		return creditCardID;
	}
	
	public void insertCustomer(Customer customer) {
		 
	}
	
	public void insertCustomers(ArrayList<Customer> customers) throws SQLException {
		 
		int count = 0;
		boolean start = false;
		
		for(Customer customer : customers){
			
			Person person = customer.getPerson();
			
			if(!start){
				
				if(person.getEmail().equals("WandaChristian@hotmail.com")){
					
					start = true;
					continue;
				}
			}
			
			if(start){
				
				if(person.getFirstName().equals("William") || person.getLastName().equals("Wolfe"))
					continue;
				
				insertPerson(person);
				
				CreditCard creditCard = customer.getCreditCard();
				String creditCardID = insertCreditCard(creditCard);
				
				SubscriptionPlan subscriptionPlan = customer.getSubscription();
				int subscriptionPlanID = insertSubscriptionPlan(subscriptionPlan);
				
				Statement statement = connection.createStatement();
				statement.executeUpdate("INSERT INTO Customer (UserID, SubscriptionPlanID, CreditCardID) "
						+ "VALUES(" + person.getUserId() + ", "
						+ subscriptionPlanID + ", "
						+ creditCardID
						+ ")");
				statement.close();
				count++;
				System.out.println("Inserted " + count + " Customers");
				//System.out.println(customer.getPerson().getFirstName() + " " + customer.getPerson().getLastName());
			}
		}
	}
	
	public void insertEmployee(Employee employee) {
		
	}
	
	public void insertGenre(String name) {
		 
	}
	
	public void insertLanguage(Language language) {
		 
	}
	   
	public void insertMovie(Movie movie) {   
		
	}
	
	public void insertMovie() {   
		
	}
	
	public void insertMovieCopy(MovieCopy movieCopy) {
		 
	}
	
	public int insertPerson(Person person) throws SQLException {
		 
		int addressID = insertAddress(person.getAddress());
		
		String birthDate = new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthDate());
		
		Statement statement = connection.createStatement();
		String insertPersonSql = "INSERT INTO Person (UserID, FirstName, LastName, BirthDate, Email, PhoneNumber, UserPassword, AddressID) "
				+ "VALUES (" + person.getUserId() + ", '"
				+ person.getFirstName() + "', '"
				+ person.getLastName() + "', "
				+ "DATE '" + birthDate + "', '"
				+ person.getEmail() + "', '"
				+ person.getPhoneNumber() + "', '"
				+ person.getPassword() + "', "
				+ addressID + ")";
		
		System.out.println(insertPersonSql);
		
		statement.executeUpdate(insertPersonSql);
		statement.close();
		
		return person.getUserId();
	}
	
	public void insertRent(Rent rent) {
		 
	}
	
	public int insertSubscriptionPlan(SubscriptionPlan subscriptionPlan) throws SQLException {
		 
		Statement statement = connection.createStatement();
		
		statement.executeUpdate("INSERT INTO SubscriptionPlan (PlanName, MonthlyCost, MaxRent, MaxRentDuration) "
				+ "VALUES ('" + subscriptionPlan.getType().name() + "', "
				+ subscriptionPlan.getMontlyCost() + ", "
				+ subscriptionPlan.getMaxRent() + ", "
				+ subscriptionPlan.getMaxRentDuration() + ")");
		
		ResultSet result = statement.executeQuery("SELECT MAX(SubscriptionPlanID) AS ID FROM SubscriptionPlan");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		return insertedRowID;
	}
}
