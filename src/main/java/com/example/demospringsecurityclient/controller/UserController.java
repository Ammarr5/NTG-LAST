package com.example.demospringsecurityclient.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.model.CustomUserDetails;
import com.example.demospringsecurityclient.model.UserProfile;
import com.example.demospringsecurityclient.service.UserService;

import javax.transaction.Transactional;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/users/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/del/{userId}")
	public boolean deleteUser(@PathVariable(value = "userId") Long id) {
		return userService.deleteEmployeeById(id);
	}
	
	@GetMapping(value = "/getuserdetails")
	public UserProfile getuserdetails(){
		UserProfile userprofile=new UserProfile();
		User user= ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		BeanUtils.copyProperties(user, userprofile);
		return userprofile;
   }
	
	
}
