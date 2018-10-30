package com.koreanunited.webflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Language;

public interface LanguageRepository extends JpaRepository<Language, String>{

	@Query(nativeQuery = true, value="select * from DomainLanguage where REGEXP_LIKE(languagename, ?1, 'i')")
	Language findRegexName(String trim);

}
