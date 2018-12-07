package com.koreanunited.webflix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.service.AnalyticsService;
import com.koreanunited.webflix.service.MovieSearchService;
import com.koreanunited.webflix.service.RentService;


@Controller
@SessionAttributes("movies")
public class MovieController {

	@Autowired
	RentService rentService;
	
	@Autowired
	MovieSearchService movieSearchService;
	
	@Autowired
	AnalyticsService analyticsService;
	
    @RequestMapping(value="/movie/{id}", method = RequestMethod.POST)
    public String rentMovie(HttpSession session, ModelMap model, @PathVariable("id") int id){
    	
    	Customer customer = (Customer) session.getAttribute("customer");
    	
    	MovieCopy  movieCopy = rentService.RentMovie(id, customer);
    	
    	if(movieCopy != null)
    		model.put("message", "You've successfully rented the movie");
    	
    	else
    		model.put("message", "Sorry you can't rent this movie");
    	
		return "message";
    }
    
    @RequestMapping(value="/movie/{id}", method = RequestMethod.GET)
    public String showMoviePage(HttpSession session, ModelMap model, @PathVariable("id") int id){
    	
    	Movie movie = movieSearchService.searchMovie(id);
    	List<Movie> suggestedMovies = analyticsService.getSuggestedMovies(movie.getId(), (Customer) session.getAttribute("customer"));
    	
    	model.put("movie", movie);
    	model.put("suggestedMovies", suggestedMovies);
    	
		return "movie";
    }
}
