package com.koreanunited.webflix.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Address;
import com.koreanunited.webflix.model.Language;
import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.model.Person;
import com.koreanunited.webflix.repository.AddressRepository;
import com.koreanunited.webflix.repository.LanguageRepository;
import com.koreanunited.webflix.repository.MovieRepository;
import com.koreanunited.webflix.repository.PersonRepository;


@RestController
@SessionAttributes("name")
public class HomeController {

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	LanguageRepository languageRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
    	
        return "home";
    }
    
    @GetMapping("/movies")
    public List<Movie> showAllMovies(){
    	
        return this.movieRepository.findAll();
    }
    
    @GetMapping("/rest/people")
    public List<Resource> showAllPeople(){
    	
        return this.personRepository.findAllPeople();
    }
    
    @GetMapping("/rest/languages")
    public List<Language> showAllLanguages(){
    	
        return this.languageRepository.findAll();
    }
    
    @GetMapping("/rest/addresses")
    public List<Address> showAllAddresses(){
    	
        return this.addressRepository.findAll();
    }
}
