package com.koreanunited.webflix.repository;

import java.util.List;

import com.koreanunited.webflix.model.Movie;

public interface MovieRepositoryCustom {

	public List<Movie> findByTitlesOrYearOfReleaseOrProductionCountriesOrLanguageOrGenresOrMovieRoles
	(List<String> titles, int minYearOfRelease, int maxYearOfRelease, List<String> countries, List<String> languages, List<String> movieGenres, List<String> artists);
}
