package com.koreanunited.webflix.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Person;

@Service
public class LoginService {

	public boolean validateUser(String username, String password) {

		Person person = null;
		
		try {
			
			DatabaseClient dbClient = DatabaseClient.getIntance();
			
			if(username.matches("\\d{7}"))
				person = dbClient.getEmployee(username, password);
			
			else
				person =  dbClient.getCustomer(username, password);
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return person != null;
	}
}
