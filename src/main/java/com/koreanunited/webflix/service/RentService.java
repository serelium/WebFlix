package com.koreanunited.webflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.repository.MovieCopyRepository;
import com.koreanunited.webflix.repository.MovieRepository;

@Service
public class RentService {

	@Autowired
	MovieCopyRepository movieCopyRepository;
	
	public boolean RentMovie(Movie movie, Customer customer)
	{
		
		MovieCopy mv = movieCopyRepository.findOneByMovieId(62622);
		System.out.println(mv);		
		return false;
	}
	
}
