package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Language;

public interface LanguageRepository extends JpaRepository<Language, String>{

	@Query(nativeQuery = true, value="select * from DomainLanguage where REGEXP_LIKE(languagename, ?1, 'i')")
	List<Language> findRegexName(String regex);
}
