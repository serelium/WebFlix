package com.koreanunited.webflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.MovieRole;

public interface MovieRoleRepository extends JpaRepository<MovieRole, Integer>{

	public List<MovieRole> findByArtist(Artist artist);
}
