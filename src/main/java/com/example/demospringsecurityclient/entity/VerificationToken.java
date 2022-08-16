package com.example.demospringsecurityclient.entity;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
	
     //10min expire time
	private static final int Expiration_Time=10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private Date expirationtime;

	@OneToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
	private User user;

	public VerificationToken(String token,User user) {
		super();
		this.token = token;
		this.user = user;
		this.expirationtime=calculateExpirationDate(Expiration_Time);
	}

	private Date calculateExpirationDate(int expirationTime) {
		Calendar calender=Calendar.getInstance();
		calender.setTimeInMillis(new Date().getTime());
		calender.add(Calendar.MINUTE, expirationTime);
		
		return new Date(calender.getTime().getTime());
	}

	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationtime=calculateExpirationDate(Expiration_Time);
	}

	
	
}
