package com.example.demospringsecurityclient.service;


import java.util.List;
import java.util.Optional;

import com.example.demospringsecurityclient.entity.User;
import com.example.demospringsecurityclient.entity.VerificationToken;
import com.example.demospringsecurityclient.model.UserModel;


public interface UserService {

	User registerUser(UserModel usermodel);

	void saveVerificationTokenForUser(String token, User user);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldtoken);

	User findUserByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	String validatePasswordResetToken(String token);

	Optional<User> getUserByPasswordResetToken(String token);

	void changePassword(User user, String newpassword);

	boolean checkIfValidOldPassword(User user, String oldpassword);

	List<User> getAllUsers();

	boolean deleteEmployeeById(Long id);




}
