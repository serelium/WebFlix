package com.koreanunited.webflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.repository.MovieCopyRepository;

@Service
public class RentService {

	@Autowired
	MovieCopyRepository movieCopyRepository;
	
	public MovieCopy RentMovie(int movieid, Customer customer)
	{
		
		List<MovieCopy> movieCopies = movieCopyRepository.findByMovieId(movieid);
		
		System.out.println(movieCopies.get(0).getCode());	
		
		return movieCopies.get(0);
	}
	
}
