package com.koreanunited.webflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.koreanunited.webflix.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByEmailAndPassword(String email, String password);
}
