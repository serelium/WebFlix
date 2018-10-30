package com.koreanunited.webflix.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.model.Rent;
import com.koreanunited.webflix.repository.MovieCopyRepository;
import com.koreanunited.webflix.repository.RentRepository;

@Service
public class RentService {

	@Autowired
	MovieCopyRepository movieCopyRepository;
	
	@Autowired
	RentRepository rentRepository;
	
	public MovieCopy RentMovie(int movieid, Customer customer)
	{
		MovieCopy movieCopyRented = null;
		
		if(customer.getSubscription().getMaxRent() > customer.getRents().size())
		{
			List<MovieCopy> movieCopies = movieCopyRepository.findByMovieId(movieid);
			
			for(MovieCopy movieCopy : movieCopies) {
				
				Rent currentRent = rentRepository.findOneByMovieCopy(movieCopy);
				
				if(currentRent == null) {
					
					movieCopyRented = movieCopy;
				}
			}
			
			if(movieCopyRented != null) {
				
				LocalDate now = LocalDate.now();
				Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				rentRepository.save(new Rent(customer, movieCopyRented, date));
			}
		}
		
		return movieCopyRented;
	}
	
}
