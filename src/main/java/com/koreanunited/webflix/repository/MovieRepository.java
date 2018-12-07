package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Country;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieGenre;

public interface MovieRepository extends JpaRepository<Movie, Integer>, MovieRepositoryCustom{

	@Query(nativeQuery = true, value="select * from Movie where REGEXP_LIKE(title, ?1, 'i')")
	public List<Movie> findRegexTitle(String regex);

	public List<Movie> findByProductionCountries(Country productionCountry);

	public List<Movie> findByGenres(MovieGenre movieGenre);

	public List<Movie> findByOriginLanguage(Language language);
	
	public List<Movie> findByYearOfReleaseBetween(int start, int end);
	
	@Query(nativeQuery = true, value="select * from Movie full Join where REGEXP_LIKE(title, ?1, 'i')")
	public List<Movie> findByQuery();
	
	@Query(nativeQuery = true, value="SELECT IDFILM, SUM(cote)*(1/COUNT(IDCLIENT)) AS rj FROM LOG6601C.COTES@db_link GROUP BY IDFILM")
	public Integer countByFilter();
}
