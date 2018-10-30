package com.koreanunited.webflix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.service.ArtistSearchService;


@Controller
public class ArtistController {

	@Autowired
	ArtistSearchService artistSearchService;
	
    @RequestMapping(value="/artist/{id}", method = RequestMethod.GET)
    public String rentMovie(HttpSession session, ModelMap model, @PathVariable("id") int id){
    	
    	Artist artist = artistSearchService.searchArtist(id);
    	
    	model.put("artist", artist);
    	
		return "artist";
    }
}
