package com.koreanunited.webflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.repository.MovieRepository;

@Service
public class MovieSearchService {

	@Autowired
	MovieRepository movieRepository;
	
	public List<Movie> searchMovies(String query){
		
		return movieRepository.findRegexTitle(".*"+ query +".*");
	}
}
