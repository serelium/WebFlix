package com.koreanunited.webflix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Country;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieGenre;
import com.koreanunited.webflix.model.MovieRole;
import com.koreanunited.webflix.repository.ArtistRepository;
import com.koreanunited.webflix.repository.CountryRepository;
import com.koreanunited.webflix.repository.LanguageRepository;
import com.koreanunited.webflix.repository.MovieGenreRepository;
import com.koreanunited.webflix.repository.MovieRepository;
import com.koreanunited.webflix.repository.MovieRoleRepository;

@Service
public class MovieSearchService {

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	MovieRoleRepository movieRoleRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	MovieGenreRepository movieGenreRepository;
	
	@Autowired
	LanguageRepository languageRepository;
	
	public List<Movie> searchMoviesFasterVersion(String searchQuery){
		
		HashMap<Integer, Movie> allMovies = new HashMap<Integer, Movie>();
    	
    	String[] splitQuery = searchQuery.split(";");
    	
    	for(String query : splitQuery) {
    		
    		List<Movie> movies = null;
    		Pattern yearPattern = Pattern.compile("\\[(\\d{4}),(\\d{4})\\]");
    		Matcher yearMatcher = yearPattern.matcher(query);
    		
			if(yearMatcher.find()) {
    			
    			int minYear = Integer.parseInt(yearMatcher.group(1));
    			int maxYear = Integer.parseInt(yearMatcher.group(2));
    			
    			if(minYear <= maxYear) {
    				
    				movies = movieRepository.findByYearOfReleaseBetween(minYear, maxYear);
    			}
    		}
    		
    		else {
    			
    			movies = movieRepository.findRegexTitle(query.trim());
    			List<Artist> artists = artistRepository.findRegexArtist(query.trim());
    			List<MovieRole> movieRoles = new ArrayList<MovieRole>();
    			List<Country> countries = countryRepository.findRegexName(query.trim());
    			List<MovieGenre> movieGenres = movieGenreRepository.findRegexName(query.trim());
    			Language language = languageRepository.findRegexName(query.trim());
    			
    			
    			if(language != null)
    				movies.addAll(movieRepository.findByOriginLanguage(language));
    			
    			for(Artist artist : artists)
    				movieRoles.addAll(movieRoleRepository.findByArtist(artist));
    			
    			for(Country country : countries)
    				movies.addAll(movieRepository.findByProductionCountries(country));
    			
    			for(MovieGenre movieGenre : movieGenres)
    				movies.addAll(movieRepository.findByGenres(movieGenre));
    			
    			for(MovieRole movieRole : movieRoles) {
    				
    				Movie movie = movieRepository.findOne(movieRole.getMovie().getId());
    				
    				if(movie != null)
    					movies.add(movie);
    			}
    		}
    		
			for(Movie movie : movies) {
				
				if(!allMovies.containsKey(movie.getId()))
					allMovies.put(movie.getId(), movie);
			}
    	}
    	
    	ArrayList<Movie> sortedMovies = new ArrayList<Movie>(allMovies.values());
    	
    	Collections.sort(sortedMovies, new Comparator<Movie>(){

			@Override
			public int compare(Movie m1, Movie m2) {
				
				return m1.getTitle().compareTo(m2.getTitle());
			}
    	});
    	
		return sortedMovies;
	}
	
	public List<Movie> searchMoviesSlowerVersion(String searchQuery){
		
		ArrayList<String> splitQuery = new ArrayList<>(Arrays.asList(searchQuery.split(";")));
		
		int minYearOfRelease = 0;
		int maxYearOfRelease = Integer.MAX_VALUE;
		Pattern yearPattern = Pattern.compile("\\[(\\d{4}),(\\d{4})\\]");
		
		for(String query : splitQuery) {
			
			Matcher yearMatcher = yearPattern.matcher(query);
			
			if(yearMatcher.find()) {
    			
				minYearOfRelease = Integer.parseInt(yearMatcher.group(1));
				maxYearOfRelease = Integer.parseInt(yearMatcher.group(2));
				
				if(minYearOfRelease > maxYearOfRelease) {
					
					minYearOfRelease = 0;
					maxYearOfRelease = Integer.MAX_VALUE;
				}
				
				splitQuery.remove(query);
				break;
    		}
		}
		
		ArrayList<Movie> sortedMovies = new ArrayList<Movie>(movieRepository
				.findByTitlesOrYearOfReleaseOrProductionCountriesOrLanguageOrGenresOrMovieRoles(splitQuery, minYearOfRelease, maxYearOfRelease, splitQuery, splitQuery, splitQuery, splitQuery));
		
		Collections.sort(sortedMovies, new Comparator<Movie>(){

			@Override
			public int compare(Movie m1, Movie m2) {
				
				return m1.getTitle().compareTo(m2.getTitle());
			}
    	});
    	
		return sortedMovies;
	}
	
	public Movie searchMovie(int id){
		
		return movieRepository.findOne(id);
	}
}
