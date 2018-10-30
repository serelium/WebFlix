package com.koreanunited.webflix.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.koreanunited.webflix.model.Movie;


@NoRepositoryBean
@Transactional
public class MovieRepositoryImpl implements MovieRepositoryCustom {


	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Movie> findByTitlesOrYearOfReleaseOrProductionCountriesOrLanguageOrGenresOrMovieRoles(
			List<String> titles, int minYearOfRelease, int maxYearOfRelease, List<String> countries,
			List<String> languages, List<String> movieGenres, List<String> artists) {
		
		String queryStr = "SELECT DISTINCT Movie.* FROM Movie " + 
				"LEFT JOIN DomainLanguage on Movie.originlanguage = DomainLanguage.languagename " + 
				"LEFT JOIN MovieGenre on Movie.movieid = MovieGenre.movieid " + 
				"LEFT JOIN Movie_Countries on Movie.movieid = Movie_Countries.movieid " + 
				"LEFT JOIN Movie_Scriptwriters on Movie.movieid = Movie_Scriptwriters.movieid " + 
				"LEFT JOIN MovieRole on Movie.movieid = MovieRole.movieid " + 
				"LEFT JOIN DomainMovieRole on MovieRole.roletype = DomainMovieRole.movierole " + 
				"LEFT JOIN Artist on MovieRole.artistid = Artist.artistid " +
                "WHERE";
		
		boolean intervalOfYear = false;
		
		if(minYearOfRelease > 0 || maxYearOfRelease < Integer.MAX_VALUE) {
			
			queryStr += " yearOfRelease BETWEEN " + minYearOfRelease + " AND " + maxYearOfRelease;
			intervalOfYear = true;
		}
		
		for(int i = 0; i < titles.size(); i++) {
			
			if(i == 0 && intervalOfYear)
				queryStr += " OR";
				
			if(i == 0)
				queryStr += String.format(" (REGEXP_LIKE(title, '.*%s.*', 'i')", titles.get(i).trim());
			
			if(i >= titles.size() - 1)
				queryStr += ")";
			
			else
				queryStr += String.format(" OR REGEXP_LIKE(title, '.*%s.*', 'i')", titles.get(i).trim());
		}
		
		
		for(int i = 0; i < countries.size(); i++) {
			
			if(i == 0)
				queryStr += String.format(" OR (REGEXP_LIKE(country, '.*%s.*', 'i')", countries.get(i).trim());
			
			if(i >= countries.size() - 1)
				queryStr += ")";
			
			else
				queryStr += String.format(" OR REGEXP_LIKE(country, '.*%s.*', 'i')", countries.get(i).trim());
		}
		
		for(int i = 0; i < languages.size(); i++) {
			
			if(i == 0)
				queryStr += String.format(" OR (REGEXP_LIKE(originlanguage, '.*%s.*', 'i')", languages.get(i).trim());
			
			if(i >= languages.size() - 1)
				queryStr += ")";
			
			else
				queryStr += String.format(" OR REGEXP_LIKE(originlanguage, '.*%s.*', 'i')", languages.get(i).trim());
		}
		
		for(int i = 0; i < movieGenres.size(); i++) {
			
			if(i == 0)
				queryStr += String.format(" OR (REGEXP_LIKE(moviegenrename, '.*%s.*', 'i')", movieGenres.get(i).trim());
			
			if(i >= movieGenres.size() - 1)
				queryStr += ")";
			
			else
				queryStr += String.format(" OR REGEXP_LIKE(moviegenrename, '.*%s.*', 'i')", movieGenres.get(i).trim());
		}
		
		for(int i = 0; i < movieGenres.size(); i++) {
			
			if(i == 0)
				queryStr += String.format(" OR (REGEXP_LIKE(artistname, '.*%s.*', 'i')", movieGenres.get(i).trim());
			
			if(i >= movieGenres.size() - 1)
				queryStr += ")";
			
			else
				queryStr += String.format(" OR REGEXP_LIKE(artistname, '.*%s.*', 'i')", movieGenres.get(i).trim());
		}
		
		
		Query query = em.createNativeQuery(queryStr, Movie.class);
		
		return query.getResultList();
	}


}
