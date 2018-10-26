package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

	@Query(nativeQuery = true, value="select * from Movie where REGEXP_LIKE(title, ?1, 'i')")
	public List<Movie> findRegexTitle(String regex);
	
	@Query(nativeQuery = true, value="select * from Movie where REGEXP_LIKE(title, ?1, 'i')")
	public List<Movie> findRegexArtist(String regex);
	
	@Query(nativeQuery = true, value="select * from Movie where REGEXP_LIKE(title, ?1, 'i')")
	public List<Movie> findRegexGenre(String regex);
}
