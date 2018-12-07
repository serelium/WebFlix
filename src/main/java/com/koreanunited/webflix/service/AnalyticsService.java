package com.koreanunited.webflix.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.Rent;
import com.koreanunited.webflix.repository.MovieRepository;
import com.koreanunited.webflix.repository.RentRepository;

@Service
public class AnalyticsService {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	RentRepository rentRepository;
	
	public int getNbOfMovieWithFilters(String ageGroup, String state, String dayOfWeek, String monthOfYear) {
		
    	JdbcTemplate template = new JdbcTemplate(dataSource);
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(template).withProcedureName("p_nb_rent_according_to_filters");
    	
    	SqlParameterSource paramMap = new MapSqlParameterSource()
    			.addValue("p_age_group", ageGroup)
    			.addValue("p_state", state)
    			.addValue("p_day_of_week", dayOfWeek)
    			.addValue("p_month_of_year", monthOfYear);
    	
    	BigDecimal count = jdbcCall.executeFunction(BigDecimal.class, paramMap);
    	
    	return count.intValueExact();
	}
	
	public List<Movie> getSuggestedMovies(int movieId, Customer customer) {
		
		class CorrelatedMovie implements Comparable<CorrelatedMovie>{
			
			public Double correlation;
			public Movie movie;
			
			public CorrelatedMovie(Movie movie, Double correlation) {
				
				this.correlation = correlation;
				this.movie = movie;
			}
			
			public int compareTo(CorrelatedMovie cm)
			{
			     if(correlation < cm.correlation)
			    	 return -1;
			     
			     if(correlation == cm.correlation)
			    	 return 0;
			     
			     else
			    	 return 1;
			}
		};
		
		List<Movie> allMovies = movieRepository.findAll();
		List<Rent> customerRents = rentRepository.findAllByCustomer(customer);
		ArrayList<CorrelatedMovie> suggestedMovies = new ArrayList<CorrelatedMovie>();
		ArrayList<Movie> moviesWithMaxCorrelation = new ArrayList<Movie>();
		
		for(Movie movie : allMovies) {
			
			for(Rent rent : customerRents) {
				
				if(rent.getMovieCopy().getMovie().getId() == movie.getId()) {
					
					allMovies.remove(movie);
				}
			}
		}
		
		for(Movie movie : allMovies) {
			
			// If Found 3 movies with correlation = 1
			if(moviesWithMaxCorrelation.size() >= 3)
				return moviesWithMaxCorrelation;
			
			if(movie.getId() == movieId)
				continue;
			
			JdbcTemplate template = new JdbcTemplate(dataSource);
			Double correlation = template.execute("Select correlation(?, ?) from dual",new PreparedStatementCallback<Double>(){
				
				@Override  
				public Double doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {  
					
					ps.setInt(1, movieId);
					ps.setInt(2, movie.getId());
					ps.execute();
					ResultSet rs = ps.getResultSet();
					rs.next();
					
					return rs.getDouble(1);  
				}
			});
			
			if(correlation == 1)
				moviesWithMaxCorrelation.add(movie);
			
			suggestedMovies.add(new CorrelatedMovie(movie, correlation));
			
			System.out.println(correlation);
		}
		
		Collections.sort(suggestedMovies, Collections.reverseOrder());
		
		ArrayList<Movie> best3Movies = new ArrayList<Movie>();
		int movieCount = 0;
		
		for(CorrelatedMovie correlatedMovie : suggestedMovies) {
			
			if(movieCount >= 3)
				break;
			
			best3Movies.add(correlatedMovie.movie);
		}
		
    	return best3Movies;
	}
}
