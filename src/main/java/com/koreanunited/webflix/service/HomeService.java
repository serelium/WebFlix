package com.koreanunited.webflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.repository.MovieRepository;

@Service
public class HomeService {

	@Autowired
	MovieRepository movieRepository;
	
	public List<Movie> getAllMovies(){
		
		return movieRepository.findAll(new Sort(new Sort.Order(Direction.ASC, "title")));
	}
}
