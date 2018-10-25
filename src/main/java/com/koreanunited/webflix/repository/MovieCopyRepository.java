package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.MovieCopy;

public interface MovieCopyRepository extends JpaRepository<MovieCopy, Integer>{

	public List<MovieCopy> findByMovieId(Integer movieid);
	
}
