import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlDataExtracter {

	public static ArrayList<Customer> extractCustomers(String xmlFIle) throws XmlPullParserException, IOException{
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        InputStream is = new FileInputStream(xmlFIle);
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
            	   if(expiryYear > today.getYear() || (expiryYear == today.getYear() && expiryMonth > today.getMonthValue()) && expiryMonth <= 12 && expiryMonth >= 0){
            	   
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
		        	   CreditCard currentCreditCard = new CreditCard(CreditCard.CreditCardType.valueOf(creditCardType), creditCardNumber, expiryMonth, expiryYear);
		        	   Person currentPerson = new Person(userId, firstName, lastName, email, currentAddress, currentBirthDate, phoneNumber.replaceAll("-", ""), password);
		        	   
		        	   customers.add(new Customer(currentPerson, currentSubscriptionPlan, currentCreditCard));
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
        
		return customers;
	}
	
	public static ArrayList<Movie> extractMovies(String xmlFIle) throws XmlPullParserException, IOException{
	
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    XmlPullParser parser = factory.newPullParser();
	
	    InputStream is = new FileInputStream(xmlFIle);
	    parser.setInput(is, null);
	
	    int eventType = parser.getEventType();
	
	    String currentTag = null, 
	           title = null,
	           language = null,
	           poster = null,
	           actor = null,
	           characterName = null,
	           director = null,
	           synposis = null;
	    
	    ArrayList<String> countries = new ArrayList<String>();
	    ArrayList<String> genres = new ArrayList<String>();
	    ArrayList<String> scriptwritters = new ArrayList<String>();
	    ArrayList<Role> roles = new ArrayList<Role>();         
	    ArrayList<String> trailers = new ArrayList<String>();
	    
	    int id = -1,
	        year = -1,
	        lenght = -1,
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
	        	 ArrayList<MovieGenre> movieGenres = generateMovieGenres(genres);
	        	 
	             id = -1;
	             year = -1;
	             lenght = -1;
	             title = null;                                 
	             language = null;                  
	             poster = null;
	             synposis = null;
	             director = null;
	             actor = null;
	             characterName = null;
	             directorID = -1;
	             roleID = -1;
	             
	             genres.clear();
	             scriptwritters.clear();
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
	                lenght = Integer.parseInt(parser.getText());
	             else if (currentTag.equals("resume"))                 
	                synposis = parser.getText();
	             else if (currentTag.equals("genre"))
	                genres.add(parser.getText());
	             else if (currentTag.equals("realisateur"))
	                director = parser.getText();
	             else if (currentTag.equals("scenariste"))
	                scriptwritters.add(parser.getText());
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
        
		return movies;
	}

	private static ArrayList<MovieGenre> generateMovieGenres(ArrayList<String> genres) {
		
		ArrayList<MovieGenre> movieGenres = new ArrayList<MovieGenre>();
		
		for(String genre : genres){
			
			movieGenres.add(new MovieGenre(genre));
		}
		
		return movieGenres;
	}
}
