package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.model.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer> {

	public Rent findOneByMovieCopy(MovieCopy moviecopy);
	
	public List<Rent> findAllByCustomer(Customer customer);
}
