package com.example.demospringsecurityclient.model;

import lombok.Data;

@Data
public class UserProfile {
	private String firstname;
    private String lastname;
	private String email;
	private String role;
}
