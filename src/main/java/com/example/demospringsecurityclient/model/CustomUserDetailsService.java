package com.example.demospringsecurityclient.model;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 //log.info("hello");		
		User user = userRepository.findByEmail(username);
		
		//log.info("hello");		
		
		 if(user ==null) {
			
	            throw new UsernameNotFoundException("User Not Found");
	        }
		 
		return new CustomUserDetails(user);
	}	

}
