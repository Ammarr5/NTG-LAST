package com.example.demospringsecurityclient.event.listener;

import java.util.UUID;

import com.example.demospringsecurityclient.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.event.RegistrationCompleteEvent;
import com.example.demospringsecurityclient.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEeventListener implements ApplicationListener<RegistrationCompleteEvent> {
	@Autowired
	private UserService userservice;

	@Autowired
	Email emailSenderService;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		// create verification token for the user with a link
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userservice.saveVerificationTokenForUser(token,user);
		
		//create email and send
		
		String url=event.getApplicationUrl() +"/verifyRegistration?token="+token;
		emailSenderService.sendMail(user.getEmail(), "Verification", "Click the link to verify your account: " + url);
		log.info("Click the link to verify your account: {}",
				url);
	}

}
