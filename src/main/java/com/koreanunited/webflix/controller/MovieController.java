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
import com.koreanunited.webflix.model.MovieCopy;
import com.koreanunited.webflix.service.RentService;


@Controller
@SessionAttributes("movies")
public class MovieController {

	@Autowired
	RentService rentService;
	
    @RequestMapping(value="/movie/{id}", method = RequestMethod.GET)
    public String showMoviePage(HttpSession session, ModelMap model, @PathVariable("id") int id){
    	
    	Customer customer = (Customer) session.getAttribute("customer");
    	
    	MovieCopy  movieCopy = rentService.RentMovie(id, customer);
    	
    	return "you just rented";
    	
    	}
}
