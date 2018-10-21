package com.koreanunited.webflix.repository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koreanunited.webflix.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query(value = "SELECT p.userid, p.firstname, p.lastname, p.birthdate, p.email, p.phonenumber, p.userpassword, p.addressid FROM person p", nativeQuery = true)
	List<Resource> findAllPeople();
}
