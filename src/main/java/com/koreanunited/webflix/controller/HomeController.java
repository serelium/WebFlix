package com.koreanunited.webflix.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Address;
import com.koreanunited.webflix.model.Artist;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.Person;
import com.koreanunited.webflix.repository.AddressRepository;
import com.koreanunited.webflix.repository.ArtistRepository;
import com.koreanunited.webflix.repository.LanguageRepository;
import com.koreanunited.webflix.repository.MovieRepository;
import com.koreanunited.webflix.repository.PersonRepository;
import com.koreanunited.webflix.service.HomeService;


@Controller
@SessionAttributes("name")
public class HomeController {

	@Autowired
	HomeService homeService;
	
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showLoginPage(HttpSession session, ModelMap model){
    	
    	Object authenticated = session.getAttribute("authenticated");
    	
    	if(authenticated != null && (boolean) authenticated) {
    		
    		List<Movie> allMovies = homeService.getAllMovies();
    		
    		session.setAttribute("movies", allMovies);
    		return "home";
    	}
    	
    	else
    		return "redirect:/login";
    }
    
    @RequestMapping(value="/home", method = RequestMethod.POST)
    public String LogOut(HttpSession session, ModelMap model){
    	
		session.removeAttribute("authenticated");
		
		return "redirect:/login";
    }
}
