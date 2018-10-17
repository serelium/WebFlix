package com.koreanunited.webflix.datainsertion;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.SQLException;
import org.xmlpull.v1.XmlPullParserException;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.service.DatabaseClient;

public class DataInsertion {

	public static void main(String[] args) {
		
		try {
			
			DatabaseClient dbClient = new DatabaseClient("log660ora12c.logti.etsmtl.ca", "equipe7", "r74w84K3", 1521, "LOG660");
			XmlDataExtractor xmlData = new XmlDataExtractor("data/customers.xml", "data/artists.xml", "data/movies.xml");
			
			ArrayList<Customer> customers = new ArrayList<Customer>(xmlData.getCustomers().values());
			ArrayList<Artist> artists = new ArrayList<Artist>(xmlData.getArtists().values());
			ArrayList<Movie> movies = new ArrayList<Movie>(xmlData.getMovies().values());
			
			dbClient.insertCustomers(customers);
			dbClient.insertArtists(artists);
			dbClient.insertMovies(movies);
			System.out.println("Done");
			
		} catch (XmlPullParserException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
