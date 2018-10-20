package com.koreanunited.webflix.service;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.koreanunited.webflix.model.Address;
import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Country;
import com.koreanunited.webflix.model.CreditCard;
import com.koreanunited.webflix.model.CreditCardType;
import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Employee;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.model.MovieGenre;
import com.koreanunited.webflix.model.MovieRole;
import com.koreanunited.webflix.model.Person;
import com.koreanunited.webflix.model.Rent;
import com.koreanunited.webflix.model.Scriptwriter;
import com.koreanunited.webflix.model.SubscriptionPlan;
import com.koreanunited.webflix.model.Trailer;

public class DatabaseClient {

	private static DatabaseClient instance;
	private Connection connection;
	private String host;
	private String username;
	private String password;
	private int port;
	private String ssid;
	
	/*public DatabaseClient(String host, String username, String password, int port, String ssid) throws SQLException {
		
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
		this.ssid = ssid;
				
		connect();
	}*/
	
	private DatabaseClient() throws SQLException {
		
		this.host = "log660ora12c.logti.etsmtl.ca";
		this.username = "equipe7";
		this.password = "r74w84K3";
		this.port = 1521;
		this.ssid = "LOG660";
				
		connect();
	}
	
	public static DatabaseClient getIntance() {
		
		if(instance == null) {
			try {
				
				instance = new DatabaseClient();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	private void connect() throws SQLException{
		
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + ssid, username, password);
	}
	
	//================================================================================
    // MovieRole
    //================================================================================
	
	public int insertMovieRole(MovieRole movieRole, Movie movie) throws SQLException {
	    
		if(movieRoleExists(movieRole, movie)){
			
			//System.out.println("Movie Role with character name \"" + movieRole.getCharacterName() + "\" for movie with id = " + movie.getId() + " already exists");
			return -1;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO MovieRole (RoleType, CharacterName, ArtistID, MovieID) "
				+ "VALUES ((SELECT MovieRole FROM DomainMovieRole WHERE DomainMovieRole.MovieRole = ?), ?, ?, ?)");
		statement.setString(1, movieRole.getType().getTypeName().name());
		statement.setString(2, movieRole.getCharacterName());
		statement.setInt(3, movieRole.getArtist().getId());
		statement.setInt(4, movie.getId());
		statement.executeUpdate();
		
		ResultSet result = statement.executeQuery("SELECT MAX(MovieRoleID) AS ID FROM MovieRole");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		//System.out.println("Inserted Movie Role with id = " + insertedRowID + " and character name \"" + movieRole.getCharacterName() + "\" for movie with id = " + movie.getId());
		
		return insertedRowID;
	}
	
	public void insertMovieRoles(List<MovieRole> cast, Movie movie) throws SQLException {
	    
		int count = 0;
		//System.out.println("@@@@@@@@@ Inserting " + cast.size() + " movie roles for movie with id = " + movie.getId() + " @@@@@@@@@");
		
		for(MovieRole movieRole : cast){
			
			if(insertMovieRole(movieRole, movie) != -1){
				
				count++;
				//System.out.println("Inserted " + count + " Artists");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + cast.size() + " movie roles for movie with id = " + movie.getId() + " @@@@@@@@@");
	}
	
	public boolean movieRoleExists(MovieRole movieRole, Movie movie) throws SQLException{
		
		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM MovieRole WHERE MovieRole.ArtistID = ? AND MovieRole.MovieID = ?");
		checkIfExists.setInt(1, movieRole.getArtist().getId());
		checkIfExists.setInt(2, movie.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Address
    //================================================================================
	
	public int insertAddress(Address address) throws SQLException {
		 
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Address (CivicNumber, Street, City, Province, PostalCode) VALUES (?, ?, ?, ?, ?)");
		
		statement.setInt(1, address.getCivicNumber());
		statement.setString(2, address.getStreet());
		statement.setString(3, address.getCity());
		statement.setString(4, address.getProvince());
		statement.setString(5, address.getPostalCode().replace(" ", ""));
		
		statement.executeUpdate();
		
		ResultSet result = statement.executeQuery("SELECT MAX(AddressID) AS ID FROM Address");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		return insertedRowID;
	}
	
	public Address getAddress(int addressId) throws SQLException {
		
		Address address = null;
		
		PreparedStatement addressStatement = connection.prepareStatement("SELECT * FROM Address WHERE Address.AddressID = ?");
		addressStatement.setInt(1, addressId);
		
		ResultSet addressResult = addressStatement.executeQuery();
		
		if(addressResult.next())
			address = new Address(addressResult.getInt("CivicNumber"), addressResult.getString("Street"), addressResult.getString("City"), addressResult.getString("Province"), addressResult.getString("PostalCode"));
		
		addressResult.close();
		addressStatement.close();
		
		return address;
	}
	
	//================================================================================
    // Artist
    //================================================================================
	
	public int insertArtist(Artist artist) throws SQLException {     
		
		if(artistExists(artist)){
			
			System.out.println("Artist with id = " + artist.getId() + " already exists");
			return -1;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Artist (ArtistID, ArtistName, BirthDate, BirthPlace, PictureLink, Biography) VALUES(?, ?, ?, ?, ?, ?)");
		
		statement.setInt(1, artist.getId());
		statement.setString(2, artist.getName());
		String dateStr = dateToString(artist.getBirthDate());
		
		if(dateStr != null)
			statement.setDate(3, java.sql.Date.valueOf(dateStr));
		else
			statement.setDate(3, null);
		
		statement.setString(4, artist.getBirthPlace());
		statement.setString(5, artist.getPictureLink());
		Clob biographyClob = connection.createClob();
		biographyClob.setString(6, artist.getBiography());
		statement.setClob(6, biographyClob);
		
		statement.executeUpdate();
		statement.close();
		
		System.out.println("Inserted artist with id = " + artist.getId());
		
		return artist.getId();
	}
	
	public void insertArtists(ArrayList<Artist> artists) throws SQLException {
		 
		int count = 0;
		System.out.println("@@@@@@@@@ Inserting " + artists.size() + " artists @@@@@@@@@");
		
		for(Artist artist : artists){
			
			if(insertArtist(artist) != -1){
				
				count++;
				System.out.println("Inserted " + count + " Artists");
			}
		}
		
		System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + artists.size() + " artists @@@@@@@@@");
	}
	
	public boolean artistExists(Artist artist) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Artist WHERE Artist.ArtistID = ?");
		checkIfExists.setInt(1, artist.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Country
    //================================================================================
	
	public String insertCountry(Country country) throws SQLException {
	    
		if(countryExists(country)){
			
			//System.out.println("Country " + country.getName() + " already exists");
			return null;
		}
		
		else if(country.getName() == null){
			
			//System.out.println("Cannot insert null country");
			return null;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Country (CountryName) VALUES (?)");
		statement.setString(1, country.getName());
		statement.executeUpdate();
		statement.close();
		//System.out.println("Inserted Country : " + country.getName());
		
		return country.getName();
	}
	
	public void insertCountries(List<Country> countries) throws SQLException {
	    
		int count = 0;
		//System.out.println("@@@@@@@@@ Inserting " + countries.size() + " countries @@@@@@@@@");
		
		for(Country country : countries){
			
			if(insertCountry(country) != null){
				
				count++;
				//System.out.println("Inserted " + count + " Countries");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + countries.size() + " countries @@@@@@@@@");
	}
	
	public boolean countryExists(Country country) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Country WHERE Country.CountryName = ?");
		checkIfExists.setString(1, country.getName());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // CreditCard
    //================================================================================

	public String insertCreditCard(CreditCard creditCard) throws SQLException {
		
		String creditCardID = creditCard.getNumber().replace(" ", "");
		
		if(creditCardExists(creditCard)){
			
			//System.out.println("Credit Card " + creditCardID + " already exists");
			return null;
		}
			
		Statement statement = connection.createStatement();
		
		String selectCreditCardTypeID = "SELECT CreditCardTypeID FROM CreditCardType WHERE CreditCardType.TypeName LIKE '" + creditCard.getType().getTypeName().name() + "'";
		ResultSet result = statement.executeQuery(selectCreditCardTypeID);
		result.next();
		
		PreparedStatement insertCreditCardStatement = connection.prepareStatement("INSERT INTO CreditCard (CreditCardID, CreditCardTypeID, ExpiryMonth, ExpiryYear) VALUES (?, ?, ?, ?)");
		insertCreditCardStatement.setString(1, creditCardID);
		insertCreditCardStatement.setInt(2, result.getInt("CreditCardTypeID"));
		insertCreditCardStatement.setInt(3, creditCard.getExpiryMonth());
		insertCreditCardStatement.setInt(4, creditCard.getExpiryYear());
		
		insertCreditCardStatement.executeUpdate();

		result.close();
		statement.close();
		insertCreditCardStatement.close();
		
		//System.out.println("Inserted Credit Card with number = " + creditCardID);
		
		return creditCardID;
	}
	
	public boolean creditCardExists(CreditCard creditCard) throws SQLException{
		
		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM CreditCard WHERE CreditCard.CreditCardID = ?");
		checkIfExists.setString(1, creditCard.getNumber().replace(" ", ""));
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	public CreditCard getCreditCard(String creditCardId) throws SQLException {
		
		CreditCard creditCard = null;
		
		PreparedStatement creditCardStatement = connection.prepareStatement("SELECT * FROM CreditCard WHERE CreditCard.CreditCardID = ?");
		creditCardStatement.setString(1, creditCardId);
		
		ResultSet creditCardResult = creditCardStatement.executeQuery();
		
		if(creditCardResult.next()) {
			
			int creditCardTypeId = creditCardResult.getInt("CreditCardTypeID");
			String creditCardNumber = creditCardResult.getString("CreditCardID");
			int expiryMonth = creditCardResult.getInt("ExpiryMonth"); 
			int expiryYear = creditCardResult.getInt("ExpiryYear");
			
			PreparedStatement creditCardTypeStatement = connection.prepareStatement("SELECT * FROM CreditCardType WHERE CreditCardType.CreditCardTypeID = ?");
			creditCardTypeStatement.setInt(1, creditCardTypeId);
			
			ResultSet creditCardTypeResult = creditCardTypeStatement.executeQuery();
			creditCardTypeResult.next();
			
			creditCard = new CreditCard(new CreditCardType(CreditCardType.ECreditCardType.valueOf(creditCardTypeResult.getString("TypeName"))), creditCardNumber, expiryMonth, expiryYear);
			
			creditCardTypeResult.close();
			creditCardTypeStatement.close();
		}
		
		creditCardResult.close();
		creditCardStatement.close();
		
		return creditCard;
	}
	
	//================================================================================
    // Customer
    //================================================================================

	
	public int insertCustomer(Customer customer) throws SQLException {
		
		Person person = customer;
		CreditCard creditCard = customer.getCreditCard();
		
		if(personExists(person)){
			
			System.out.println("Cannot insert Customer with id = " + person.getUserId() + " because this person already exists");
			return -1;
		}
		
		else if(personEmailExists(person)){
			
			System.out.println("Cannot insert Customer with email = " + person.getEmail() + " because this email already exists");
			return -1;
		}
		
		if(creditCardExists(creditCard)){
			
			System.out.println("Cannot insert Customer with id = " + person.getUserId() + " because this person's credit card (" + creditCard.getNumber() + ") already exists");
			return -1;
		}
			
		insertPerson(person);
		String creditCardID = insertCreditCard(creditCard);
		
		SubscriptionPlan subscriptionPlan = customer.getSubscription();
		int subscriptionPlanID = insertSubscriptionPlan(subscriptionPlan);
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer (UserID, SubscriptionPlanID, CreditCardID) VALUES (?, ?, ?)");
		statement.setInt(1, person.getUserId());
		statement.setInt(2, subscriptionPlanID);
		statement.setString(3, creditCardID);
		
		statement.executeUpdate();
		statement.close();
		
		System.out.println("Inserted Customer with id = " + person.getUserId());
		
		return person.getUserId();
	}
	
	public void insertCustomers(ArrayList<Customer> customers) throws SQLException {
		 
		int count = 0;
		
		System.out.println("@@@@@@@@@ Inserting " + customers.size() + " customers @@@@@@@@@");
		
		for(Customer customer : customers){
			
			if(count > 0 && count % 5000 == 0)
				connect();
			
			if(insertCustomer(customer) != -1){
				
				count++;
				System.out.println("Inserted " + count + " Customers");
			}
		}
		
		System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + customers.size() + " customers @@@@@@@@@");
	}
	
	public Customer getCustomer(String username, String password) throws SQLException {
		
		Customer customer = null;
		
		PreparedStatement personStatement = connection.prepareStatement("SELECT * FROM Person WHERE Person.Email = ? AND Person.UserPassword = ?");
		personStatement.setString(1, username);
		personStatement.setString(2, password);
		
		ResultSet resultPerson = personStatement.executeQuery();
		
		if(resultPerson.next()) {
			
			int userId = resultPerson.getInt("UserID");
			String firstName = resultPerson.getString("FirstName");
			String lastName = resultPerson.getString("LastName");
			Date birthDate  = resultPerson.getDate("BirthDate");
			String email = resultPerson.getString("Email");
			String phoneNumber = resultPerson.getString("PhoneNumber");
			String userPassword = resultPerson.getString("UserPassword");
			int addressId = resultPerson.getInt("AddressID");
					
			PreparedStatement customerStatement = connection.prepareStatement("SELECT * FROM Customer WHERE Customer.UserID = ?");
			customerStatement.setInt(1, userId);
			
			ResultSet resultCustomer = customerStatement.executeQuery();
			
			if(resultCustomer.next()) {
				
				int subscriptionPlanId =  resultCustomer.getInt("SubscriptionPlanID");
				String creditCardId = resultCustomer.getString("CreditCardID");
				
				Address address = getAddress(addressId);
				CreditCard creditCard = getCreditCard(creditCardId);
				SubscriptionPlan subscriptionPlan =  getSubscriptionPlan(subscriptionPlanId);
				
				customer = new Customer(userId, firstName, lastName, email, address, birthDate, phoneNumber, userPassword, subscriptionPlan, creditCard);
			}
			
			resultCustomer.close();
			customerStatement.close();
		}
		
		personStatement.close();
		resultPerson.close();
		
		return customer;
	}
	
	//================================================================================
    // Employee
    //================================================================================
	
	public int insertEmployee(Employee employee) throws SQLException {
		
		Person person = employee;
		
		if(personExists(person)){
			
			//System.out.println("Cannot insert Employee with id = " + person.getUserId() + " because this person already exists");
			return -1;
		}
		
		else if(personEmailExists(person)){
			
			//System.out.println("Cannot insert Employee with email = " + person.getEmail() + " because this email already exists");
			return -1;
		}
		
		else if(employeeExists(employee)){
			
			//System.out.println("Cannot insert Employee with regeristration id = " + employee.getRegristrationNumber() + " because this regeristration id already exists");
			return -1;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Employee (UserID, RegeristrationID) VALUES (?, ?)");
		statement.setInt(1, person.getUserId());
		statement.setInt(2, employee.getRegristrationNumber());
		
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted Employee with id = " + person.getUserId());
		
		return person.getUserId();
	}
	
	public boolean employeeExists(Employee employee) throws SQLException{
		
		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Employee WHERE Employee.RegeristrationID = ?");
		checkIfExists.setInt(1, employee.getRegristrationNumber());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	public Employee getEmployee(String username, String password) {
		
		return null;
	}
	
	//================================================================================
    // DomainMovieGenre
    //================================================================================
	
	public String insertDomainMovieGenre(MovieGenre movieGenre) throws SQLException {
		
		if(domainMovieGenreExists(movieGenre)){
			
			//System.out.println("Domain movie genre \"" + movieGenre.getName() + "\" already exists");
			return null;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO DomainMovieGenre (MovieGenreName) VALUES (?)");
		statement.setString(1, movieGenre.getName());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted domain movie genre \"" + movieGenre.getName() + "\"");
		
		return movieGenre.getName();
	}
	
	public void insertDomainMovieGenres(List<MovieGenre> movieGenres) throws SQLException {
		
		int count = 0;
		
		//System.out.println("@@@@@@@@@ Inserting " + movieGenres.size() + " domain movie genres @@@@@@@@@");
		
		for(MovieGenre movieGenre : movieGenres){
			
			if(insertDomainMovieGenre(movieGenre) != null){
				
				count++;
				//System.out.println("Inserted " + count + " domain movie genres");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + movieGenres.size() + " domain movie genres @@@@@@@@@");
	}
	
	public boolean domainMovieGenreExists(MovieGenre movieGenre) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM DomainMovieGenre WHERE DomainMovieGenre.MovieGenreName = ?");
		checkIfExists.setString(1, movieGenre.getName());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // MovieGenre
    //================================================================================
	
	public boolean insertMovieGenre(MovieGenre movieGenre, Movie movie) throws SQLException {
		
		if(movieGenreExists(movieGenre, movie)){
			
			//System.out.println("Movie genre \"" + movieGenre.getName() + "\" for movie with id = " + movie.getId() + " already exists");
			return false;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO MovieGenre (MovieGenreName, MovieID) VALUES (?, ?)");
		statement.setString(1, movieGenre.getName());
		statement.setInt(2, movie.getId());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted movie genre \"" + movieGenre.getName() + "\" for movie with id = " + movie.getId());
		
		return true;
	}
	
	public void insertMovieGenres(List<MovieGenre> movieGenres, Movie movie) throws SQLException {
		
		int count = 0;
		
		//System.out.println("@@@@@@@@@ Inserting " + movieGenres.size() + " movie genres @@@@@@@@@");
		
		for(MovieGenre movieGenre : movieGenres){
			
			if(insertMovieGenre(movieGenre, movie)){
				
				count++;
				//System.out.println("Inserted " + count + " movie genres");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + movieGenres.size() + " movie genres @@@@@@@@@");
	}
	
	public boolean movieGenreExists(MovieGenre movieGenre, Movie movie) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM MovieGenre WHERE MovieGenre.MovieGenreName = ? AND MovieGenre.MovieID = ?");
		checkIfExists.setString(1, movieGenre.getName());
		checkIfExists.setInt(2, movie.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Language
    //================================================================================
	
	public String insertLanguage(Language language) throws SQLException {
		
		if(languageExists(language)){
			
			//System.out.println("Language \"" + language.getName() + "\" already exists");
			return null;
		}
		
		else if(language.getName() == null){
			
			//System.out.println("Cannot insert null language");
			return null;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO DomainLanguage (LanguageName) VALUES (?)");
		statement.setString(1, language.getName());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted language \"" + language.getName() + "\"");
		
		return language.getName();
	}
	
	public boolean languageExists(Language language) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM DomainLanguage WHERE DomainLanguage.LanguageName = ?");
		checkIfExists.setString(1, language.getName());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Trailer
    //================================================================================
	
	private int insertTrailer(Trailer trailer, Movie movie) throws SQLException {
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Trailer (TrailerLink, MovieID) VALUES (?, ?)");
		statement.setString(1, trailer.getTrailerLink());
		statement.setInt(2, movie.getId());
		statement.executeUpdate();
		
		ResultSet result = statement.executeQuery("SELECT MAX(TrailerID) AS ID FROM Trailer");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		//System.out.println("Inserted Trailer with link = \"" + trailer.getTrailerLink() + "\"");
		
		return insertedRowID;
	}
	
	private void insertTrailers(List<Trailer> trailers, Movie movie) throws SQLException {
		
		int count = 0;
		
		//System.out.println("@@@@@@@@@ Inserting " + trailers.size() + " trailers for movie with id = " + movie.getId() + " @@@@@@@@@");
		
		for(Trailer trailer : trailers){
			
			if(insertTrailer(trailer, movie) != -1){
				
				count++;
				//System.out.println("Inserted " + count + " trailers");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + trailers.size() + " trailers for movie with id = " + movie.getId() + " @@@@@@@@@");
	}
	
	//================================================================================
    // Movie
    //================================================================================
	   
	public int insertMovie(Movie movie) throws SQLException {   
		
		if(movieExists(movie)){
			
			System.out.println("Movie with id = " + movie.getId() + " already exists");
			return -1;
		}
		
		insertDomainMovieGenres(movie.getGenres());
		insertCountries(movie.getProductionCountries());
		insertLanguage(movie.getOriginLanguage());
		insertScripwritters(movie.getScriptwriters());
		
		String sql = "INSERT INTO Movie (MovieID, Title, YearOfRelease, MovieLength, OriginLanguage, PosterLink, Synopsis) "
				+ "VALUES (?, ?, ?, ?, (SELECT LanguageName FROM DomainLanguage WHERE DomainLanguage.LanguageName = ?), ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, movie.getId());
		statement.setString(2, movie.getTitle());
		statement.setInt(3, movie.getYearOfRelease());
		statement.setInt(4, movie.getLength());
		statement.setString(5, movie.getOriginLanguage().getName());
		statement.setString(6, movie.getPosterLink());
		statement.setString(7, movie.getSynopsis());
		
		statement.executeUpdate();
		statement.close();
		
		insertMovieGenres(movie.getGenres(), movie);
		
		if(movie.getDirector().getArtist() != null)
			insertMovieRole(movie.getDirector(), movie);
		
		insertMovieRoles(movie.getCast(), movie);
		insertMovieCountries(movie.getProductionCountries(), movie);
		insertMovieScriptwriters(movie.getScriptwriters(), movie);
		insertTrailers(movie.getTrailers(), movie);
		
		System.out.println("Inserted Movie with id = " + movie.getId() + " and tilte = \"" + movie.getTitle() + "\"");
		
		return movie.getId();
	}

	public void insertMovies(ArrayList<Movie> movies) throws SQLException {   
		
		int count = 0;
		
		System.out.println("@@@@@@@@@ Inserting " + movies.size() + " movies @@@@@@@@@");
		
		for(Movie movie : movies){
			
			if(insertMovie(movie) != -1){
				
				count++;
				System.out.println("Inserted " + count + " movies");
			}
		}
		
		System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + movies.size() + " movies @@@@@@@@@");
	}
	
	public boolean movieExists(Movie movie) throws SQLException{
		
		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Movie WHERE Movie.MovieID = ?");
		checkIfExists.setInt(1, movie.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Movie_Countries
    //================================================================================
	
	public boolean insertMovieCountry(Country country, Movie movie) throws SQLException {
		
		if(movieCountryExists(country, movie)){
			
			//System.out.println("Movie_Country with country \"" + country.getName() + "\" and movie id = " + movie.getId() + " already exists");
			return false;
		}

		PreparedStatement statement = connection.prepareStatement("INSERT INTO Movie_Countries (Country, MovieID) VALUES (?, ?)");
		statement.setString(1, country.getName());
		statement.setInt(2, movie.getId());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted Movie_Country with country \"" + country.getName() + "\" and movie id = " + movie.getId());
		
		return true;
	}
	
	public void insertMovieCountries(List<Country> countries, Movie movie) throws SQLException {
		
		int count = 0;
		
		//System.out.println("@@@@@@@@@ Inserting " + countries.size() + " countries for movie with id = " + movie.getId() + " @@@@@@@@@");
		
		for(Country country : countries){
			
			if(insertMovieCountry(country, movie)){
				
				count++;
				//System.out.println("Inserted " + count + " Movie_Countries");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + countries.size() + " countries for movie with id = " + movie.getId() + " @@@@@@@@@");
	}
	
	public boolean movieCountryExists(Country country, Movie movie) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Movie_Countries WHERE Movie_Countries.Country = ? AND Movie_Countries.MovieID = ?");
		checkIfExists.setString(1, country.getName());
		checkIfExists.setInt(2, movie.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Movie_Scriptwriters
    //================================================================================
	
	public boolean insertScriptwriter(Scriptwriter scriptwriter, Movie movie) throws SQLException {
		
		if(movieScriptwriterExists(scriptwriter, movie)){
			
			//System.out.println("Movie_Scriptwriters with country \"" + scriptwriter.getName() + "\" and movie id = " + movie.getId() + " already exists");
			return false;
		}

		PreparedStatement statement = connection.prepareStatement("INSERT INTO Movie_Scriptwriters (ScriptwriterName, MovieID) VALUES (?, ?)");
		statement.setString(1, scriptwriter.getName());
		statement.setInt(2, movie.getId());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted Movie_Scriptwriter with scriptwriter \"" + scriptwriter.getName() + "\" and movie id = " + movie.getId());
		
		return true;
	}
	
	public void insertMovieScriptwriters(List<Scriptwriter> scriptwriters, Movie movie) throws SQLException {
		
		int count = 0;
		
		//System.out.println("@@@@@@@@@ Inserting " + scriptwriters.size() + " scriptwriters for movie with id = " + movie.getId() + " @@@@@@@@@");
		
		for(Scriptwriter scriptwriter : scriptwriters){
			
			if(insertScriptwriter(scriptwriter, movie)){
				
				count++;
				//System.out.println("Inserted " + count + " Movie_Scriptwriters");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + scriptwriters.size() + " scriptwriters for movie with id = " + movie.getId() + " @@@@@@@@@");
	}
	
	public boolean movieScriptwriterExists(Scriptwriter scriptwriter, Movie movie) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Movie_Scriptwriters WHERE Movie_Scriptwriters.ScriptwriterName = ? AND Movie_Scriptwriters.MovieID = ?");
		checkIfExists.setString(1, scriptwriter.getName());
		checkIfExists.setInt(2, movie.getId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Scriptwriter
    //================================================================================

	public String insertScripwriter(Scriptwriter scriptwriter) throws SQLException {
		
		if(scriptwriterExists(scriptwriter)){
			
			//System.out.println("Scriptwriter with name \"" + scriptwriter.getName() + "\" already exists");
			return null;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Scriptwriter (FullName) VALUES (?)");
		statement.setString(1, scriptwriter.getName());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted Scriptwriter with name \"" + scriptwriter.getName() + "\"");
		
		return scriptwriter.getName();
	}
	
	public void insertScripwritters(List<Scriptwriter> scriptwriters) throws SQLException {
		
		int count = 0;
		//System.out.println("@@@@@@@@@ Inserting " + scriptwriters.size() + " scriptwriters @@@@@@@@@");
		
		for(Scriptwriter scriptwriter : scriptwriters){
			
			if(insertScripwriter(scriptwriter) != null){
				
				count++;
				//System.out.println("Inserted " + count + " scriptwriters");
			}
		}
		
		//System.out.println("@@@@@@@@@ Finished inserting " + count + "/" + scriptwriters.size() + " scriptwriters @@@@@@@@@");
	}
	
	public boolean scriptwriterExists(Scriptwriter scriptwriter) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Scriptwriter WHERE Scriptwriter.FullName = ?");
		checkIfExists.setString(1, scriptwriter.getName());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	

	//================================================================================
    // MovieCopy
    //================================================================================
	
	public int insertMovieCopy(MovieCopy movieCopy) throws SQLException {
		 
		PreparedStatement statement = connection.prepareStatement("INSERT INTO MovieCopy (MovieCopyID, MovieID) VALUES (?, ?)");
		statement.setInt(1, movieCopy.getCode());
		statement.setInt(2, movieCopy.getMovie().getId());
		statement.executeUpdate();
		statement.close();
		
		//System.out.println("Inserted Movie Copy with code = " + movieCopy.getCode());
		
		return movieCopy.getCode();
	}
	
	//================================================================================
    // Person
    //================================================================================
	
	private int insertPerson(Person person) throws SQLException {
		
		int addressID = insertAddress(person.getAddress());
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Person (UserID, FirstName, LastName, BirthDate, Email, PhoneNumber, UserPassword, AddressID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setInt(1, person.getUserId());
		statement.setString(2, person.getFirstName());
		statement.setString(3, person.getLastName());
		
		String dateStr = dateToString(person.getBirthDate());
		if(dateStr != null)
			statement.setDate(4, java.sql.Date.valueOf(dateStr));
		else
			statement.setDate(4, null);
		
		statement.setString(5, person.getEmail());
		statement.setString(6, person.getPhoneNumber());
		statement.setString(7, person.getPassword());
		statement.setInt(8, addressID);
		
		statement.executeUpdate();
		statement.close();
		
		return person.getUserId();
	}
	
	public boolean personExists(Person person) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Person WHERE Person.UserID = ?");
		checkIfExists.setInt(1, person.getUserId());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	public boolean personEmailExists(Person person) throws SQLException{

		PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM Person WHERE Person.Email = ?");
		checkIfExists.setString(1, person.getEmail());
		ResultSet existsResultSet = checkIfExists.executeQuery();
		
		boolean exists = existsResultSet.next();
		checkIfExists.close();
		existsResultSet.close();
		
		return exists;
	}
	
	//================================================================================
    // Rent
    //================================================================================
	
	public int insertRent(Rent rent, Customer customer) throws SQLException {
		 
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Rent (MovieCopyID, RentTime, UserID) VALUES (?, ?, ?, ?)");
		statement.setInt(1, rent.getMovieCopy().getCode());
		
		String rentDate = dateToString(rent.getRentTime());
		if(rentDate != null)
			statement.setDate(2, java.sql.Date.valueOf(rentDate));
		else
			statement.setDate(2, null);
		
		statement.setInt(3, customer.getUserId());
		
		statement.executeUpdate();
		
		ResultSet result = statement.executeQuery("SELECT MAX(RentID) AS ID FROM Rent");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		//System.out.println("Inserted Rent with id = " + insertedRowID);
		
		return insertedRowID;
	}
	
	//================================================================================
    // SubscriptionPlan
    //================================================================================
	
	public int insertSubscriptionPlan(SubscriptionPlan subscriptionPlan) throws SQLException {
		 
		PreparedStatement statement = connection.prepareStatement("INSERT INTO SubscriptionPlan (PlanName, MonthlyCost, MaxRent, MaxRentDuration) VALUES (?, ?, ?, ?)");
		statement.setString(1, subscriptionPlan.getType());
		statement.setDouble(2, subscriptionPlan.getMontlyCost());
		statement.setInt(3, subscriptionPlan.getMaxRent());
		statement.setInt(4, subscriptionPlan.getMaxRentDuration());
		
		statement.executeUpdate();
		
		ResultSet result = statement.executeQuery("SELECT MAX(SubscriptionPlanID) AS ID FROM SubscriptionPlan");
		result.next();
		int insertedRowID = result.getInt("ID");
		result.close();
		statement.close();
		
		//System.out.println("Inserted Subscription Plan with id = " + insertedRowID);
		
		return insertedRowID;
	}
	
	public SubscriptionPlan getSubscriptionPlan(int subscriptionPlanId) throws SQLException {
		
		SubscriptionPlan subscriptionPlan =  null;
		
		PreparedStatement subscriptionPlanStatement = connection.prepareStatement("SELECT * FROM SubscriptionPlan WHERE SubscriptionPlan.SubscriptionPlanID = ?");
		subscriptionPlanStatement.setInt(1, subscriptionPlanId);
		
		ResultSet subscriptionPlanResult = subscriptionPlanStatement.executeQuery();
		subscriptionPlanResult.next();
		
		subscriptionPlan = new SubscriptionPlan(SubscriptionPlan.SubscriptionPlanType.valueOf(subscriptionPlanResult.getString("PlanName")), 
				subscriptionPlanResult.getDouble("MonthlyCost"), 
				subscriptionPlanResult.getInt("MaxRent"), 
				subscriptionPlanResult.getInt("MaxRentDuration"));
		
		subscriptionPlanResult.close();
		subscriptionPlanStatement.close();
		
		return subscriptionPlan;
	}
	
	//================================================================================
    // Util
    //================================================================================
	
	private String dateToString(Date date){
		
		return  date == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}
