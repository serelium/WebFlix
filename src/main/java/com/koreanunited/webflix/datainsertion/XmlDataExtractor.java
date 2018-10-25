package com.koreanunited.webflix.datainsertion;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.koreanunited.webflix.model.Address;
import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Country;
import com.koreanunited.webflix.model.CreditCard;
import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieGenre;
import com.koreanunited.webflix.model.MovieRole;
import com.koreanunited.webflix.model.Person;
import com.koreanunited.webflix.model.Scriptwriter;
import com.koreanunited.webflix.model.SubscriptionPlan;
import com.koreanunited.webflix.model.Trailer;
import com.koreanunited.webflix.model.CreditCardType;
import com.koreanunited.webflix.model.MovieRoleType;
import com.koreanunited.webflix.model.SubscriptionPlan.SubscriptionPlanType;

public class XmlDataExtractor {

	private HashMap<Integer, Customer> allCustomers;
	private HashMap<Integer, Artist> allArtists;
	private HashMap<Integer, Movie> allMovies;
	private HashMap<Integer, MovieRole> allMovieRoles;
	private ArrayList<MovieGenre> allMovieGenres;
	private ArrayList<Language> allLanguages; 
	private ArrayList<Country> allCountries;
	private ArrayList<Scriptwriter> allScriptwriters;
	
	public XmlDataExtractor(String customersXmlFilePath, String artistsXmlFilePath, String moviesXmlFilePath) throws XmlPullParserException, IOException {
		
		allCustomers = new HashMap<Integer, Customer>();
		allArtists = new HashMap<Integer, Artist>();
		allMovies = new HashMap<Integer, Movie>();
		allMovieRoles = new HashMap<Integer, MovieRole>();
		allMovieGenres = new ArrayList<MovieGenre>();
		allLanguages = new ArrayList<Language>();
		allCountries = new ArrayList<Country>();
		allScriptwriters = new ArrayList<Scriptwriter>();
		
		extractCustomers(customersXmlFilePath);
		extractArtists(artistsXmlFilePath);
		extractMovies(moviesXmlFilePath);
	}

	public HashMap<Integer, Customer> getCustomers() {
		return allCustomers;
	}

	public HashMap<Integer, Artist> getArtists() {
		return allArtists;
	}

	public HashMap<Integer, Movie> getMovies() {
		return allMovies;
	}

	public HashMap<Integer, MovieRole> getMovieRoles() {
		return allMovieRoles;
	}

	public ArrayList<MovieGenre> getMovieGenres() {
		return allMovieGenres;
	}

	public ArrayList<Language> getLanguages() {
		return allLanguages;
	}

	public ArrayList<Country> getCountries() {
		return allCountries;
	}
	
	public ArrayList<Scriptwriter> getScriptwriters() {
		return allScriptwriters;
	}

	private void extractCustomers(String customersXmlFilePath) throws XmlPullParserException, IOException{
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        InputStream is = new FileInputStream(customersXmlFilePath);
        parser.setInput(is, null);

        int eventType = parser.getEventType();
        
        String currentTag = null, 
                lastName = null,
                firstName = null,
                email = null,
                phoneNumber = null,
                birthDate = null,
                address = null,
                city = null,
                province = null,
                postalCode = null,
        		creditCardType = null,
                creditCardNumber = null,
                password = null,
                subscriptionPlan = null;                                 
         
         int userId = -1,
             expiryMonth = -1,
             expiryYear = -1;
        
        while (eventType != XmlPullParser.END_DOCUMENT) 
        {
        	if(eventType == XmlPullParser.START_TAG) 
            {
        		currentTag = parser.getName();
               
        		if (currentTag.equals("client") && parser.getAttributeCount() == 1)
                  userId = Integer.parseInt(parser.getAttributeValue(0));
            }
        	else if (eventType == XmlPullParser.END_TAG) 
            {                              
               currentTag = null;
               
               if (parser.getName().equals("client") && userId >= 0)
               {   
            	   
            	   LocalDate today = java.time.LocalDate.now();
            	   if((expiryYear > today.getYear() || (expiryYear == today.getYear()) && expiryMonth > today.getMonthValue()) && expiryMonth <= 12 && expiryMonth > 0){
            	   
            		   if(expiryMonth <=0 || expiryMonth > 12)
            			   System.out.println("test");
            		   
		    	       Pattern civicNumberPattern = Pattern.compile("(\\d+)\\s+(.+)");
		    	       Matcher civicNumberPatternMatcher = civicNumberPattern.matcher(address);
		    	       civicNumberPatternMatcher.find();
		    	       
		    	       int civicNumber = Integer.parseInt(civicNumberPatternMatcher.group(1));
		    	       String street = civicNumberPatternMatcher.group(2).replace("'", "''");
		    	       city = city.replace("'", "''");
		    	       
		    	       Date currentBirthDate = null;
		    	       
		    	       try {
		    	    	   currentBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
		    	       } catch (ParseException e) {
		    	    	   e.printStackTrace();
		    	       }   
		    	       SubscriptionPlan currentSubscriptionPlan = null;
		    	       
		    	       switch(subscriptionPlan){
		    	       
		    	       	case "D":
		    	       		currentSubscriptionPlan = new SubscriptionPlan(SubscriptionPlan.SubscriptionPlanType.Beginner, 5, 1, 10);
		    	       		break;
		    	       	case "I":
		    	       		currentSubscriptionPlan = new SubscriptionPlan(SubscriptionPlan.SubscriptionPlanType.Beginner, 10, 5, 30);
		    	       		break;
		    	       	case "A":
		    	       		currentSubscriptionPlan = new SubscriptionPlan(SubscriptionPlan.SubscriptionPlanType.Beginner, 15, 10, -1);
		    	       		break;
		    	       }
		    	       
		        	   Address currentAddress = new Address(civicNumber, street, city, province, postalCode);
		        	   CreditCard currentCreditCard = new CreditCard(new CreditCardType(CreditCardType.ECreditCardType.valueOf(creditCardType)), creditCardNumber, expiryMonth, expiryYear);
		        	   
		        	   allCustomers.put(userId, new Customer(userId, firstName, lastName, email, currentAddress, currentBirthDate, phoneNumber.replaceAll("-", ""), password, currentSubscriptionPlan, currentCreditCard));
            	   }
	        	   
	               lastName = null;
	               firstName = null;
	               email = null;
	               phoneNumber = null;
	               birthDate = null;
	               address = null;
	               city = null;
	               province = null;
	               postalCode = null;
	               creditCardType = null;
	               creditCardNumber = null;
	               password = null;
	               subscriptionPlan = null;    
               }
            }
        	
        	else if (eventType == XmlPullParser.TEXT && userId >= 0) 
            {         
               if (currentTag != null)
               {                                    
                  if (currentTag.equals("nom-famille"))
                     lastName = parser.getText();
                  else if (currentTag.equals("prenom"))
                	 firstName = parser.getText();
                  else if (currentTag.equals("courriel"))
                     email = parser.getText();
                  else if (currentTag.equals("tel"))
                     phoneNumber = parser.getText();
                  else if (currentTag.equals("anniversaire"))
                     birthDate = parser.getText();
                  else if (currentTag.equals("adresse"))
                     address = parser.getText();
                  else if (currentTag.equals("ville"))
                     city = parser.getText();
                  else if (currentTag.equals("province"))
                     province = parser.getText();
                  else if (currentTag.equals("code-postal"))
                     postalCode = parser.getText();
                  else if (currentTag.equals("carte"))
                     creditCardType = parser.getText();
                  else if (currentTag.equals("no"))
                	 creditCardNumber = parser.getText();
                  else if (currentTag.equals("exp-mois"))                 
                     expiryMonth = Integer.parseInt(parser.getText());
                  else if (currentTag.equals("exp-annee"))                 
                     expiryYear = Integer.parseInt(parser.getText());
                  else if (currentTag.equals("mot-de-passe"))                 
                     password = parser.getText();  
                  else if (currentTag.equals("forfait"))                 
                     subscriptionPlan = parser.getText(); 
               }              
            }
            
            eventType = parser.next();
        }
	}
	
	private void extractArtists(String artistsXmlFilePath) throws XmlPullParserException, IOException {
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        InputStream is = new FileInputStream(artistsXmlFilePath);
        parser.setInput(is, null);

        int eventType = parser.getEventType();

        String currentTag = null, 
               name = null,
               birthDate = null,
               birthPlace = null,
               picture = null,
               biography = null;
        
        int artistID = -1;
        
        while (eventType != XmlPullParser.END_DOCUMENT) 
        {
           if(eventType == XmlPullParser.START_TAG) 
           {
              currentTag = parser.getName();
              
              if (currentTag.equals("personne") && parser.getAttributeCount() == 1)
                 artistID = Integer.parseInt(parser.getAttributeValue(0));
           } 
           else if (eventType == XmlPullParser.END_TAG) 
           {                              
              currentTag = null;
              
              if (parser.getName().equals("personne") && artistID >= 0)
              {
                 //TODO INSERT HERE
            	  
            	 Date currentBirthDate = null;
            	
				 try {
					 if(birthDate != null)
						 currentBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
				 } catch (ParseException e) {
				 	e.printStackTrace();
				 }
				 
	        	 Artist artist = new Artist(artistID, name, currentBirthDate, birthPlace, picture, biography);
	        	 allArtists.put(artist.getId(), artist);
	        	  
	             artistID = -1;
	             name = null;
	             birthDate = null;
	             birthPlace = null;
	             picture = null;
	             biography = null;
              }
           }
           else if (eventType == XmlPullParser.TEXT && artistID >= 0) 
           {
              if (currentTag != null)
              {                                    
                 if (currentTag.equals("nom"))
                    name = parser.getText();
                 else if (currentTag.equals("anniversaire"))
                    birthDate = parser.getText();
                 else if (currentTag.equals("lieu"))
                    birthPlace = parser.getText();
                 else if (currentTag.equals("photo"))
                    picture = parser.getText();
                 else if (currentTag.equals("bio"))
                    biography = parser.getText();
              }              
           }
           
           eventType = parser.next();            
        }
	}
	
	private void extractMovies(String moviesXmlFilePath) throws XmlPullParserException, IOException{
		
		HashSet<String> languageHashSet = new HashSet<String>();
		HashSet<String> movieGenreHashSet = new HashSet<String>();
		HashSet<String>	countryHashSet = new HashSet<String>();
		HashSet<String>	scriptwriterHashSet = new HashSet<String>();
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    XmlPullParser parser = factory.newPullParser();
	
	    InputStream is = new FileInputStream(moviesXmlFilePath);
	    parser.setInput(is, null);
	
	    int eventType = parser.getEventType();
	
	    String currentTag = null, 
	           title = null,
	           language = null,
	           poster = null,
	           actor = null,
	           characterName = null,
	           director = null,
	           synopsis = null;
	    
	    ArrayList<String> countries = new ArrayList<String>();
	    ArrayList<String> genres = new ArrayList<String>();
	    ArrayList<String> scriptwriters = new ArrayList<String>();
	    ArrayList<Role> roles = new ArrayList<Role>();         
	    ArrayList<String> trailers = new ArrayList<String>();
	    
	    int id = -1,
	        year = -1,
	        length = -1,
	        roleID = -1,
	        directorID = -1;
	    
	    while (eventType != XmlPullParser.END_DOCUMENT) 
	    {
	       if(eventType == XmlPullParser.START_TAG) 
	       {
	          currentTag = parser.getName();
	          
	          if (currentTag.equals("film") && parser.getAttributeCount() == 1)
	             id = Integer.parseInt(parser.getAttributeValue(0));
	          else if (currentTag.equals("realisateur") && parser.getAttributeCount() == 1)
	             directorID = Integer.parseInt(parser.getAttributeValue(0));
	          else if (currentTag.equals("acteur") && parser.getAttributeCount() == 1)
	             roleID = Integer.parseInt(parser.getAttributeValue(0));
	       } 
	       else if (eventType == XmlPullParser.END_TAG) 
	       {                              
	          currentTag = null;
	          
	          if (parser.getName().equals("film") && id >= 0)
	          {
	        	 ArrayList<MovieGenre> movieGenres = new ArrayList<MovieGenre>();
	        	 ArrayList<Country> productionCountries = new ArrayList<Country>();
	        	 ArrayList<Artist> movieArtists = new ArrayList<Artist>();
	        	 ArrayList<MovieRole> movieRoles = new ArrayList<MovieRole>();
	        	 ArrayList<Scriptwriter> scriptwritersInMovie = new ArrayList<Scriptwriter>();
	        	 ArrayList<Trailer> movieTrailers = new ArrayList<Trailer>();
	        	 
	        	 for(String genre : genres){
	        		 
	        		 if(genre == null)
	        			 continue;
	        		 
	        		 movieGenreHashSet.add(genre);
	        		 movieGenres.add(new MovieGenre(genre));
	        	 }
	        	 
	        	 for(String country : countries){
	        		 
	        		 if(country == null)
	        			 continue;
	        		 
	        		 countryHashSet.add(country);
	        		 productionCountries.add(new Country(country));
	        	 }
	        	 
	        	 for(Role role : roles){
	        		 
	        		 Artist movieActor = allArtists.get(role.id);
	        		 MovieRole movieRole = new MovieRole(new MovieRoleType(MovieRoleType.EMovieRoleType.Actor), role.personnage, movieActor);
	        		 
	        		 movieArtists.add(movieActor);
	        		 movieRoles.add(movieRole);
	        		 this.allMovieRoles.put(role.id, movieRole);
	        	 }
	        	 
	        	 for(String scriptwriter : scriptwriters){
	        		 
	        		 if(scriptwriter == null)
	        			 continue;
	        		 
	        		 scriptwriterHashSet.add(scriptwriter);
	        		 scriptwritersInMovie.add(new Scriptwriter(scriptwriter));
	        	 }
	        	 
	        	 for(String trailerLink : trailers){
	        		 
	        		 if(trailerLink == null)
	        			 continue;
	        		 
	        		 movieTrailers.add(new Trailer(trailerLink));
	        	 }
	        	 
        		 if(language != null)
        			 languageHashSet.add(language);
	        	 
	        	 Artist directorArtist = allArtists.get(directorID);
	        	 MovieRole directorMovieRole = new MovieRole(new MovieRoleType(MovieRoleType.EMovieRoleType.Director), null, directorArtist);
	        	 
	        	 if(directorArtist != null) {
	        		 
	        		 movieArtists.add(directorArtist);
	        	 	movieRoles.add(directorMovieRole);
	        	 }
	        	 
	        	 Movie movie = new Movie(id, title, year, productionCountries, new Language(language), length, movieGenres, scriptwritersInMovie, movieRoles, movieTrailers, poster, synopsis);
	        	 
	        	 allMovies.put(id, movie);
	             id = -1;
	             year = -1;
	             length = -1;
	             title = null;                                 
	             language = null;                  
	             poster = null;
	             synopsis = null;
	             director = null;
	             actor = null;
	             characterName = null;
	             directorID = -1;
	             roleID = -1;
	             
	             genres.clear();
	             scriptwriters.clear();
	             roles.clear();
	             trailers.clear();  
	             countries.clear();
	          }
	          if (parser.getName().equals("role") && roleID >= 0) 
	          {              
	             roles.add(new Role(roleID, actor, characterName));
	             roleID = -1;
	             actor = null;
	             characterName = null;
	          }
	       }
	       else if (eventType == XmlPullParser.TEXT && id >= 0) 
	       {
	          if (currentTag != null)
	          {                                    
	             if (currentTag.equals("titre"))
	                title = parser.getText();
	             else if (currentTag.equals("annee"))
	                year = Integer.parseInt(parser.getText());
	             else if (currentTag.equals("pays"))
	                countries.add(parser.getText());
	             else if (currentTag.equals("langue"))
	                language = parser.getText();
	             else if (currentTag.equals("duree"))                 
	                length = Integer.parseInt(parser.getText());
	             else if (currentTag.equals("resume"))                 
	                synopsis = parser.getText();
	             else if (currentTag.equals("genre"))
	                genres.add(parser.getText());
	             else if (currentTag.equals("realisateur"))
	                director = parser.getText();
	             else if (currentTag.equals("scenariste"))
	                scriptwriters.add(parser.getText());
	             else if (currentTag.equals("acteur"))
	                actor = parser.getText();
	             else if (currentTag.equals("personnage"))
	                characterName = parser.getText();
	             else if (currentTag.equals("poster"))
	                poster = parser.getText();
	             else if (currentTag.equals("annonce"))
	                trailers.add(parser.getText());                  
	          }              
	       }
	       
	       eventType = parser.next();            
	    }
	    
	    for(String movieGenre : movieGenreHashSet)
	    	this.allMovieGenres.add(new MovieGenre(movieGenre));
	    
	    for(String originLanguage : languageHashSet)
	    	this.allLanguages.add(new Language(originLanguage));
	    
	    for(String country : countryHashSet)
	    	this.allCountries.add(new Country(country));
	    
	    for(String scriptwriter : scriptwriterHashSet)
	    	this.allScriptwriters.add(new Scriptwriter(scriptwriter));
	}
}
