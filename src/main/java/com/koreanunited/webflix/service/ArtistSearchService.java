package com.koreanunited.webflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.repository.ArtistRepository;

@Service
public class ArtistSearchService {

	@Autowired
	ArtistRepository artistRepository;
	
	public Artist searchArtist(int id) {
		
		return artistRepository.findOne(id);
	}
}
