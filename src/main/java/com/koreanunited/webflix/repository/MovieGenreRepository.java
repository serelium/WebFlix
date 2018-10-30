package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, String>{

	@Query(nativeQuery = true, value="select * from DomainMovieGenre where REGEXP_LIKE(moviegenrename, ?1, 'i')")
	List<MovieGenre> findRegexName(String trim);
}
