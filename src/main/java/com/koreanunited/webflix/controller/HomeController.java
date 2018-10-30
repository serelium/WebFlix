package com.koreanunited.webflix.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.repository.MovieCopyRepository;
import com.koreanunited.webflix.repository.MovieRepository;
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
	MovieRepository movieRepository;
	
	@Autowired
	MovieCopyRepository movieCopyRepository;
	
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
    public String searchMovies(HttpSession session, ModelMap model, @RequestParam String searchQuery){
    	
    	List<Movie> movies = null;
    	
    	if(searchQuery.isEmpty())
    		movies = homeService.getAllMovies();
    	
    	else
    		movies = movieSearchService.searchMoviesFasterVersion(searchQuery);
    	
    	session.setAttribute("movies", movies);
		
		return "redirect:/home";
    }
    
    @RequestMapping(value="/insertmoviecopies", method = RequestMethod.GET)
    public String insertMovieCopies(HttpSession session, ModelMap model){
    	
    	Random random = new Random();
    	
    	for(Movie movie : movieRepository.findAll()) {
    		
    		int nbOfCopies = random.nextInt(10);
    		
    		for(int i = 0; i < nbOfCopies; i++) {
    			
    			MovieCopy moviecopy = new MovieCopy(movie);
    			
    			movieCopyRepository.save(moviecopy);
    		}
    	}
		
		return "redirect:/success";
    }
    
    @RequestMapping(value="/message", method = RequestMethod.GET)
    public String message(@RequestParam String message, ModelMap model){
		
    	model.put("message", message);
    	
		return "message";
    }
}
