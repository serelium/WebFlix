package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Country;

public interface CountryRepository extends JpaRepository<Country, String>{

	@Query(nativeQuery = true, value="select * from Country where REGEXP_LIKE(countryname, ?1, 'i')")
	List<Country> findRegexName(String regex);
}
