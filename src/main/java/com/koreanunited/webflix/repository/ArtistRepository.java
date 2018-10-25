package com.koreanunited.webflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
