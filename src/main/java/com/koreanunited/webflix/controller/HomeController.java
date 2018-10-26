package com.koreanunited.webflix.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieRole;
import com.koreanunited.webflix.repository.ArtistRepository;
import com.koreanunited.webflix.repository.MovieRepository;
import com.koreanunited.webflix.repository.MovieRoleRepository;
import com.koreanunited.webflix.service.HomeService;
import com.koreanunited.webflix.service.MovieSearchService;


@Controller
@SessionAttributes("name")
public class HomeController {

	@Autowired
	HomeService homeService;
	
	@Autowired
	MovieSearchService movieSearchService;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	MovieRoleRepository movieRoleRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showLoginPage(HttpSession session, ModelMap model){
    	
    	Object authenticated = session.getAttribute("authenticated");
    	
    	if(authenticated != null && (boolean) authenticated) {
    		
    		if(session.getAttribute("movies") == null) {
    			
    			List<Movie> allMovies = homeService.getAllMovies();
    			session.setAttribute("movies", allMovies);
    		}
    		return "home";
    	}
    	
    	else
    		return "redirect:/login";
    }
    
    @RequestMapping(value="/home", method = RequestMethod.POST)
    public String logout(HttpSession session, ModelMap model){
    	
    	session.removeAttribute("authenticated");
    	
		return "redirect:/login";
    }
    
    @RequestMapping(value="/search")
    public String LogOut(HttpSession session, ModelMap model, @RequestParam String searchQuery){
    	
    	HashMap<Integer, Movie> allMovies = new HashMap<Integer, Movie>();
    	
    	String[] splitQuery = searchQuery.split(";");
    	
    	for(String query : splitQuery) {
    		
    		List<Movie> movies = movieSearchService.searchMovies(query.trim());
    		List<Artist> artists = artistRepository.findRegexArtist(query);
    		List<MovieRole> movieRoles = new ArrayList<MovieRole>();
    		
    		for(Artist artist : artists)
    			movieRoles.addAll(movieRoleRepository.findByArtist(artist));
			
			for(MovieRole movieRole : movieRoles)
				movies.add(movieRepository.findOne(movieRole.getId()));
    		
    		for(Movie movie : movies) {
    			
    			if(!allMovies.containsKey(movie.getId()))
    				allMovies.put(movie.getId(), movie);
    		}
    	}
    	
    	session.setAttribute("movies", allMovies.values());
		
		return "redirect:/home";
    }
}
