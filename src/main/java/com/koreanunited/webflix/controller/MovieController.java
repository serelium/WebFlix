package com.koreanunited.webflix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Movie;
import com.koreanunited.webflix.service.HomeService;
import com.koreanunited.webflix.service.RentService;


@Controller
@SessionAttributes("movies")
public class MovieController {

	@Autowired
	RentService rentService;
	
    @RequestMapping(value="/movie", method = RequestMethod.GET)
    public String showMoviePage(HttpSession session, ModelMap model){
    		
    	rentService.RentMovie(null,null);
    	
    	return "ca marche";
    	
    	}
}
