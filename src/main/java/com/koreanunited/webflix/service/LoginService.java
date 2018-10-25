package com.koreanunited.webflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanunited.webflix.model.Customer;
import com.koreanunited.webflix.model.Person;
import com.koreanunited.webflix.repository.CustomerRepository;

@Service
public class LoginService {

	@Autowired
	CustomerRepository customerRepository;
	
	public Customer validateUser(String username, String password) {

		Person person = null;
		
		/*try {
			
			DatabaseClient dbClient = DatabaseClient.getIntance();
			
			if(username.matches("\\d{7}"))
				person = dbClient.getEmployee(username, password);
			
			else
				person =  dbClient.getCustomer(username, password);
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		

		Customer customer = customerRepository.findByEmail(username);
		customer.getSubscription();
		customer.getAddress();
		customer.getCreditCard();
		
		return customer;
	}
}
