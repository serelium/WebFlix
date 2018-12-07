package com.koreanunited.webflix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreanunited.webflix.service.AnalyticsService;

@Controller
public class AnalyticsController {

	@Autowired
	AnalyticsService analyticsService;
	
    @RequestMapping(value="/analytics", method = RequestMethod.GET)
    public String getNbOfMoviesWithFilters(HttpSession session, ModelMap model){
		
    	return "analytics";
    }
    
    @RequestMapping(value="/count", method = RequestMethod.POST)
    public String searchMovies(HttpSession session, ModelMap model, @RequestParam String ageGroup, @RequestParam String province, @RequestParam String day, @RequestParam String month){
    	
    	if(ageGroup.isEmpty())
    		ageGroup = ".*";
    	if(province.isEmpty())
    		province = ".*";
    	if(day.isEmpty())
    		day = ".*";
    	if(month.isEmpty())
    		month = ".*";
    	
    	return String.valueOf(analyticsService.getNbOfMovieWithFilters(ageGroup, province, day, month));
    }
}
