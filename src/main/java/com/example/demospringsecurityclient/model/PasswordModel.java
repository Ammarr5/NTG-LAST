package com.example.demospringsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordModel {
	private String email;
	private String oldpassword;
	private String newpassword;
}
