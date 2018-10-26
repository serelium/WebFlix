package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

	@Query(nativeQuery = true, value="select * from Artist where REGEXP_LIKE(artistname, ?1, 'i')")
	public List<Artist> findRegexArtist(String regex);
}
