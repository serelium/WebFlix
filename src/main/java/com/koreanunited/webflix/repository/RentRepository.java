package com.koreanunited.webflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.model.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer> {

	public Rent findOneByMovieCopy(MovieCopy moviecopy);
}
