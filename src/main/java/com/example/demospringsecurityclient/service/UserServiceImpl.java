package com.example.demospringsecurityclient.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demospringsecurityclient.entity.PasswordResetToken;
import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.entity.VerificationToken;
import com.example.demospringsecurityclient.model.UserModel;
import com.example.demospringsecurityclient.repository.ResetPasswordTokenRepository;
import com.example.demospringsecurityclient.repository.UserRepository;
import com.example.demospringsecurityclient.repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userReposityory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	
	@Override
	public User registerUser(UserModel usermodel) {
		
		User user=new User();
		user.setEmail(usermodel.getEmail());
		user.setFirstname(usermodel.getFirstname());
		user.setLastname(usermodel.getLastname());
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(usermodel.getPassword()));
		userReposityory.save(user);
		return user;
	}

	@Override
	public void saveVerificationTokenForUser(String token, User user) {
	
		VerificationToken verificationToken = new VerificationToken(token,user);
		verificationTokenRepository.save(verificationToken);
		
	}

	@Override
	public String validateVerificationToken(String token) {
		
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if(verificationToken==null) {
			return "invalid";
		}
		User user = verificationToken.getUser();
		Calendar calender =Calendar.getInstance();
		if((verificationToken.getExpirationtime().getTime()-calender.getTime().getTime()) <=0) {
			
			verificationTokenRepository.delete(verificationToken);
			return "expired";
		}
		user.setEnabled(true);
		userReposityory.save(user);
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldtoken) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(oldtoken);
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepository.save(verificationToken);
		return verificationToken;
	}

	@Override
	public User findUserByEmail(String email) {
	
		return userReposityory.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(token,user);
		resetPasswordTokenRepository.save(passwordResetToken);
	}

	@Override
	public String validatePasswordResetToken(String token) {
		PasswordResetToken passwordResetToken=resetPasswordTokenRepository.findByToken(token);
		if(passwordResetToken==null) {
			return "invalid";
		}
		User user = passwordResetToken.getUser();
		Calendar calender =Calendar.getInstance();
		if((passwordResetToken.getExpirationtime().getTime()-calender.getTime().getTime()) <=0) {
			resetPasswordTokenRepository.delete(passwordResetToken);
			return "expired";
		}
		return "valid";
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(resetPasswordTokenRepository.findByToken(token).getUser());
	}

	@Override
	public void changePassword(User user, String newpassword) {
		user.setPassword(passwordEncoder.encode(newpassword));
		userReposityory.save(user);
		
	}

	@Override
	public boolean checkIfValidOldPassword(User user, String oldpassword) {
		
		return passwordEncoder.matches(oldpassword, user.getPassword());
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userReposityory.findAll();
	}

	@Override
	public boolean deleteEmployeeById(Long id) {
		if (id != null) {
			VerificationToken verificationToken = verificationTokenRepository.findByUser_Id(id);
			PasswordResetToken resetPasswordToken = resetPasswordTokenRepository.findByUser_Id(id);
			
			
			if(verificationToken==null ) {
				if(resetPasswordToken==null ) {
				userReposityory.deleteById(id);
			    }
				else {
					resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
					userReposityory.deleteById(id);
				}
			}
			else if(resetPasswordToken==null) {
				verificationTokenRepository.deleteById(verificationToken.getId());
				userReposityory.deleteById(id);
			}
			else {	
				verificationTokenRepository.deleteById(verificationToken.getId());
				resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
				userReposityory.deleteById(id);
			}
			
			return true;
		}

		return false;
	

  }
}

