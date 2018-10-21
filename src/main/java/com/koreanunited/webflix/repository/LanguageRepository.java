package com.koreanunited.webflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.Language;

public interface LanguageRepository extends JpaRepository<Language, String>{

}
