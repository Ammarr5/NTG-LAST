package com.example.demospringsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demospringsecurityclient.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

	User findByEmail(String email);



	//User findByFirstname(String username);
   
	
}
