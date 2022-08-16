package com.example.demospringsecurityclient.controller;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demospringsecurityclient.entity.Email;
import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.entity.VerificationToken;
import com.example.demospringsecurityclient.event.RegistrationCompleteEvent;
import com.example.demospringsecurityclient.model.PasswordModel;
import com.example.demospringsecurityclient.model.UserModel;

import com.example.demospringsecurityclient.service.UserService;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
public class RegistrationController {
	@Autowired
	private UserService userservice;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	Email emailSenderService;
	
	@PostMapping("/register")
	public void registerUser(@RequestBody UserModel usermodel ,final HttpServletRequest request) {
		User user=userservice.registerUser(usermodel);
		publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

	}
	
	@GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam("token") String token) {
		String result=userservice.validateVerificationToken(token);
		if(result.equalsIgnoreCase("valid")) {
			return "user verified successfully";
		}
		return "bad user";
	}
         
	@GetMapping("/resendverifytoken")
	public String resendVerificationToken(@RequestParam("token") String oldtoken ,HttpServletRequest request) {
		
		VerificationToken verificationToken = userservice.generateNewVerificationToken(oldtoken);
		User user=verificationToken.getUser();
		 resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
	        return "Verification Link Sent again";
	}
	
	//this function if the user is not logged in and forgot his password
	@PostMapping("/resetpassword")
	public String resetPassword(@RequestBody PasswordModel passwordmodel  ,HttpServletRequest request) {
		User user=userservice.findUserByEmail(passwordmodel.getEmail());
		String url ="";
		if(user!=null) {
			String token=UUID.randomUUID().toString(); 
			userservice.createPasswordResetTokenForUser(user,token);
			url =passwordResetTokenEmail(user,applicationUrl(request),token);
		}
			
		return url;
	}
	
	
	 @PostMapping("/savePassword")
	    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
	        String result = userservice.validatePasswordResetToken(token);
	        if(!result.equalsIgnoreCase("valid")) {
	            return "Invalid Token";
	        }
	        Optional<User> user = userservice.getUserByPasswordResetToken(token); 
	        if(user.isPresent()) {
	        	//now lets change the password with a new password
	        	userservice.changePassword(user.get(), passwordModel.getNewpassword());
	            return "Password Reset Successfully";
	        } else {
	            return "Invalid Token";
	        }
	    }
	 
	   //this function if the user is logged in and need to change his password
	    @PostMapping("/changePassword")
	    public String changePassword(@RequestBody PasswordModel passwordModel){
	        User user = userservice.findUserByEmail(passwordModel.getEmail());
	        if(!userservice.checkIfValidOldPassword(user,passwordModel.getOldpassword())) {
	            return "Invalid Old Password";
	        }
	        //Save New Password
	        userservice.changePassword(user,passwordModel.getNewpassword());
	        return "Password Changed Successfully";
	    }
	
	
	 private String passwordResetTokenEmail(User user, String applicationUrl, String token) {
		
		 String url =
	                applicationUrl
	                        + "/savePassword?token="
	                        + token;

		 emailSenderService.sendMail(user.getEmail(), "Reset Password", "Click the link to reset your password: " + url);
		log.info("Click the link to reset your password: {}",
				url);
		return url;
	}

	private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
	        String url =
	                applicationUrl
	                        + "/verifyRegistration?token="
	                        + verificationToken.getToken();

		emailSenderService.sendMail(user.getEmail(), "Verification", "Click the link to verify your account: " + url);
		log.info("Click the link to verify your account: {}",
	                url);
	    }

	private String applicationUrl(HttpServletRequest request) {
		
		return "http://"+request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	
}
