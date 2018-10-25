package com.koreanunited.webflix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.service.LoginService;

@Controller
@SessionAttributes("username")
public class LoginController {

    @Autowired
    LoginService service;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String showWelcomePage(HttpSession session, ModelMap model, @RequestParam String username, @RequestParam String password){

    	Customer customer = service.validateUser(username, password);
    	
        if (customer == null) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }

        session.setAttribute("customer", customer);
        session.setAttribute("authenticated", true);
        model.put("username", username);
        model.put("password", password);

        return "redirect:/home";
    }

}
